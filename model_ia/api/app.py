from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
import tensorflow as tf
import numpy as np
from PIL import Image
from io import BytesIO
import os
import logging

# ==================== CONFIGURATION ====================
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MODEL_PATH = os.path.join(BASE_DIR, 'models', 'sunuagri_model.h5')
CLASSES_PATH = os.path.join(BASE_DIR, 'models', 'classes.txt')
IMG_SIZE = (224, 224)

# ==================== CHARGEMENT DU MODÈLE ====================
logger.info("🔄 Chargement du modèle...")

if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError(f"Modèle introuvable : {MODEL_PATH}")

model = tf.keras.models.load_model(MODEL_PATH)

with open(CLASSES_PATH, 'r', encoding='utf-8') as f:
    CLASSES = f.read().splitlines()

logger.info(f"✅ Modèle chargé : {len(CLASSES)} classes")

# Dictionnaire de traduction pour l'affichage utilisateur
NOMS_FR = {
    'Potato___Early_blight': 'Alternariose (pomme de terre)',
    'Potato___Late_blight': 'Mildiou (pomme de terre)',
    'Potato___healthy': 'Pomme de terre saine',
    'Tomato___Bacterial_spot': 'Tache bactérienne (tomate)',
    'Tomato___Early_blight': 'Alternariose (tomate)',
    'Tomato___Late_blight': 'Mildiou (tomate)',
    'Tomato___Leaf_Mold': 'Cladosporiose (tomate)',
    'Tomato___Septoria_leaf_spot': 'Septoriose (tomate)',
    'Tomato___Target_Spot': 'Tache ciblée (tomate)',
    'Tomato___Tomato_Yellow_Leaf_Curl_Virus': 'Virus YLCV (tomate)',
    'Tomato___Tomato_mosaic_virus': 'Virus mosaïque (tomate)',
    'Tomato___healthy': 'Tomate saine',
}

# Dictionnaire culture associée
CULTURE_MAP = {
    'Potato___Early_blight': 'Pomme de terre',
    'Potato___Late_blight': 'Pomme de terre',
    'Potato___healthy': 'Pomme de terre',
    'Tomato___Bacterial_spot': 'Tomate',
    'Tomato___Early_blight': 'Tomate',
    'Tomato___Late_blight': 'Tomate',
    'Tomato___Leaf_Mold': 'Tomate',
    'Tomato___Septoria_leaf_spot': 'Tomate',
    'Tomato___Target_Spot': 'Tomate',
    'Tomato___Tomato_Yellow_Leaf_Curl_Virus': 'Tomate',
    'Tomato___Tomato_mosaic_virus': 'Tomate',
    'Tomato___healthy': 'Tomate',
}

# ==================== APPLICATION FASTAPI ====================
app = FastAPI(
    title="SunuAgri API - Diagnostic IA",
    description="API de diagnostic des maladies des plantes (tomate, pomme de terre)",
    version="1.0.0"
)

# CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # En production, mettre l'URL de Spring Boot
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ==================== ENDPOINTS ====================

@app.get("/")
def accueil():
    """Page d'accueil de l'API."""
    return {
        "service": "SunuAgri Diagnostic IA",
        "version": "1.0.0",
        "status": "en ligne",
        "classes": len(CLASSES),
        "endpoints": {
            "GET /": "Page d'accueil",
            "GET /health": "Vérifier l'état du service",
            "GET /classes": "Liste des maladies supportées",
            "POST /predict": "Analyser une image (multipart/form-data)"
        }
    }


@app.get("/health")
def health():
    """Vérification de l'état du service."""
    return {
        "status": "ok",
        "model_loaded": model is not None,
        "classes": len(CLASSES)
    }


@app.get("/classes")
def get_classes():
    """Retourne la liste des classes supportées."""
    return {
        "total": len(CLASSES),
        "classes": [
            {
                "code": c,
                "nom_fr": NOMS_FR.get(c, c),
                "culture": CULTURE_MAP.get(c, "Inconnue"),
                "saine": "healthy" in c.lower()
            }
            for c in CLASSES
        ]
    }


@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    """
    Analyse une image de feuille et retourne le diagnostic.

    - **file** : image JPG/PNG à analyser
    """
    # Validation
    if not file.content_type.startswith("image/"):
        raise HTTPException(
            status_code=400,
            detail=f"Format non supporté : {file.content_type}. Envoyez une image JPG/PNG."
        )

    try:
        # Lire l'image
        contents = await file.read()
        image = Image.open(BytesIO(contents)).convert('RGB')
        image = image.resize(IMG_SIZE)

        # Convertir en array (⚠️ PAS de division par 255, le modèle le fait)
        arr = np.array(image).astype(np.float32)
        arr = np.expand_dims(arr, axis=0)

        # Prédiction
        preds = model.predict(arr, verbose=0)[0]
        idx = int(np.argmax(preds))
        classe = CLASSES[idx]
        confiance = float(preds[idx])

        # Top 3
        top3_idx = np.argsort(preds)[-3:][::-1]
        top3 = [
            {
                "code": CLASSES[int(i)],
                "nom_fr": NOMS_FR.get(CLASSES[int(i)], CLASSES[int(i)]),
                "confiance": round(float(preds[i]) * 100, 2)
            }
            for i in top3_idx
        ]

        logger.info(f"✅ Prédiction : {classe} ({confiance*100:.2f}%)")

        return JSONResponse({
            "success": True,
            "maladie": {
                "code": classe,
                "nom_fr": NOMS_FR.get(classe, classe),
                "culture": CULTURE_MAP.get(classe, "Inconnue"),
                "saine": "healthy" in classe.lower()
            },
            "confiance": round(confiance * 100, 2),
            "top3": top3,
            "message": (
                f"Plante saine détectée" if "healthy" in classe.lower()
                else f"Maladie détectée : {NOMS_FR.get(classe, classe)}"
            )
        })

    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"❌ Erreur : {e}")
        raise HTTPException(status_code=500, detail=f"Erreur d'analyse : {str(e)}")


# ==================== DÉMARRAGE ====================
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host="0.0.0.0",
        port=8000,
        reload=True
    )