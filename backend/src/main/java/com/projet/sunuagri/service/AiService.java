package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.AiPredictionResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AiService {

    /**
     * Envoie une image au microservice IA (FastAPI) et retourne le diagnostic.
     */
    AiPredictionResponseDTO analyserImage(MultipartFile image);

    /**
     * Vérifie que le microservice IA est disponible.
     */
    boolean verifierDisponibilite();
}