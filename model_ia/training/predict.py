import tensorflow as tf
import numpy as np
from PIL import Image
import sys
import os

MODEL_PATH = 'models/sunuagri_model.h5'
CLASSES_PATH = 'models/classes.txt'

print("🔄 Chargement du modèle...")
model = tf.keras.models.load_model(MODEL_PATH)

with open(CLASSES_PATH, 'r', encoding='utf-8') as f:
    classes = f.read().splitlines()

print(f"✅ Modèle chargé : {len(classes)} classes\n")


def predict(image_path):
    if not os.path.exists(image_path):
        print(f"❌ Fichier introuvable : {image_path}")
        return

    # Charger et redimensionner
    img = Image.open(image_path).convert('RGB').resize((224, 224))
    arr = np.array(img)

    print(f"🔎 DEBUG — Valeurs image :")
    print(f"   min={arr.min()}, max={arr.max()}, mean={arr.mean():.2f}")
    print(f"   shape={arr.shape}, dtype={arr.dtype}\n")

    # ⚠️ NE PAS diviser par 255 — le modèle contient déjà Rescaling(1./255)
    arr = arr.astype(np.float32)
    arr = np.expand_dims(arr, axis=0)

    # Prédire
    preds = model.predict(arr, verbose=0)[0]
    idx = np.argmax(preds)

    print(f"📷 Image : {image_path}")
    print(f"🔍 Classe prédite : {classes[idx]}")
    print(f"📊 Confiance : {preds[idx]*100:.2f}%\n")

    print("Top 3 :")
    top3 = np.argsort(preds)[-3:][::-1]
    for i in top3:
        bar = "█" * int(preds[i] * 30)
        print(f"  {classes[i]:45s} {preds[i]*100:6.2f}%  {bar}")


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python training/predict.py <chemin_image>")
        sys.exit(1)

    image_path = ' '.join(sys.argv[1:])
    predict(image_path)