# training/train.py
import tensorflow as tf
from tensorflow.keras import layers, models, applications
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint, ReduceLROnPlateau
import os

# ==================== CONFIGURATION ====================
DATA_DIR = 'data/processed'
MODEL_DIR = 'models'
IMG_SIZE = (224, 224)
BATCH_SIZE = 32
EPOCHS_HEAD = 15       # Étape 1 : transfer learning (base gelée)
EPOCHS_FINETUNE = 10   # Étape 2 : fine-tuning
SEED = 42

os.makedirs(MODEL_DIR, exist_ok=True)

# ==================== CHARGEMENT DES DONNÉES ====================
print("📂 Chargement des données...")

train_ds = tf.keras.utils.image_dataset_from_directory(
    DATA_DIR,
    validation_split=0.2,
    subset="training",
    seed=SEED,
    image_size=IMG_SIZE,
    batch_size=BATCH_SIZE
)

val_ds = tf.keras.utils.image_dataset_from_directory(
    DATA_DIR,
    validation_split=0.2,
    subset="validation",
    seed=SEED,
    image_size=IMG_SIZE,
    batch_size=BATCH_SIZE
)

class_names = train_ds.class_names
num_classes = len(class_names)

print(f"✅ {num_classes} classes trouvées :")
for i, name in enumerate(class_names):
    print(f"   {i}. {name}")

# Sauvegarde des classes pour l'API
with open(os.path.join(MODEL_DIR, 'classes.txt'), 'w', encoding='utf-8') as f:
    f.write('\n'.join(class_names))

# ==================== OPTIMISATION ====================
AUTOTUNE = tf.data.AUTOTUNE
train_ds = train_ds.cache().shuffle(1000, seed=SEED).prefetch(AUTOTUNE)
val_ds = val_ds.cache().prefetch(AUTOTUNE)

# ==================== DATA AUGMENTATION ====================
# Compense le déséquilibre et améliore la généralisation
data_augmentation = tf.keras.Sequential([
    layers.RandomFlip("horizontal"),
    layers.RandomRotation(0.15),
    layers.RandomZoom(0.15),
    layers.RandomContrast(0.1),
])

# ==================== MODÈLE : MobileNetV2 ====================
base_model = applications.MobileNetV2(
    input_shape=(224, 224, 3),
    include_top=False,
    weights='imagenet'
)
base_model.trainable = False  # Étape 1 : base gelée

inputs = layers.Input(shape=(224, 224, 3))
x = data_augmentation(inputs)
x = layers.Rescaling(1./255)(x)
x = base_model(x, training=False)
x = layers.GlobalAveragePooling2D()(x)
x = layers.Dropout(0.3)(x)
outputs = layers.Dense(num_classes, activation='softmax')(x)

model = models.Model(inputs, outputs)

# ==================== ÉTAPE 1 : Transfer Learning ====================
print("\n🔵 ÉTAPE 1 : Transfer learning (base gelée)")

model.compile(
    optimizer=tf.keras.optimizers.Adam(learning_rate=0.001),
    loss='sparse_categorical_crossentropy',
    metrics=['accuracy']
)

model.summary()

callbacks_head = [
    EarlyStopping(monitor='val_loss', patience=5, restore_best_weights=True),
    ModelCheckpoint(
        os.path.join(MODEL_DIR, 'best_model_head.h5'),
        monitor='val_accuracy',
        save_best_only=True
    ),
]

history_head = model.fit(
    train_ds,
    validation_data=val_ds,
    epochs=EPOCHS_HEAD,
    callbacks=callbacks_head
)

# ==================== ÉTAPE 2 : Fine-tuning ====================
print("\n🟢 ÉTAPE 2 : Fine-tuning (dernières couches dégelées)")

base_model.trainable = True

# Geler toutes les couches sauf les 30 dernières
for layer in base_model.layers[:-30]:
    layer.trainable = False

model.compile(
    optimizer=tf.keras.optimizers.Adam(learning_rate=1e-5),  # LR très faible
    loss='sparse_categorical_crossentropy',
    metrics=['accuracy']
)

callbacks_finetune = [
    EarlyStopping(monitor='val_loss', patience=5, restore_best_weights=True),
    ModelCheckpoint(
        os.path.join(MODEL_DIR, 'best_model.h5'),
        monitor='val_accuracy',
        save_best_only=True
    ),
    ReduceLROnPlateau(monitor='val_loss', factor=0.5, patience=3, min_lr=1e-7),
]

history_finetune = model.fit(
    train_ds,
    validation_data=val_ds,
    epochs=EPOCHS_FINETUNE,
    callbacks=callbacks_finetune
)

# ==================== SAUVEGARDE FINALE ====================
final_path = os.path.join(MODEL_DIR, 'sunuagri_model.h5')
model.save(final_path)
print(f"\n✅ Modèle final sauvegardé : {final_path}")

# ==================== EXPORT TFLITE ====================
print("\n📦 Export TensorFlow Lite...")
converter = tf.lite.TFLiteConverter.from_keras_model(model)
converter.optimizations = [tf.lite.Optimize.DEFAULT]
tflite_model = converter.convert()

tflite_path = os.path.join(MODEL_DIR, 'sunuagri_model.tflite')
with open(tflite_path, 'wb') as f:
    f.write(tflite_model)
print(f"✅ TFLite sauvegardé : {tflite_path}")

print("\n🎉 Entraînement terminé !")