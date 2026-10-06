package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.DiagnosticAnalyseResponseDTO;
import com.projet.sunuagri.dto.DiagnosticCreateDTO;
import com.projet.sunuagri.dto.DiagnosticDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiagnosticService {

    DiagnosticDTO creer(DiagnosticCreateDTO dto);

    DiagnosticDTO trouverParId(Long id);

    List<DiagnosticDTO> trouverTous();

    List<DiagnosticDTO> trouverParUtilisateur(Long utilisateurId);

    DiagnosticDTO modifier(Long id, DiagnosticCreateDTO dto);

    void supprimer(Long id);

    /**
     * Analyse une image avec l'IA et enregistre le diagnostic.
     */
    DiagnosticAnalyseResponseDTO analyser(
            MultipartFile image,
            Long utilisateurId
    );
}