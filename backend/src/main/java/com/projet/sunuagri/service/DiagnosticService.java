package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.DiagnosticCreateDTO;
import com.projet.sunuagri.dto.DiagnosticDTO;

import java.util.List;

public interface DiagnosticService {

    DiagnosticDTO creer(DiagnosticCreateDTO dto);

    DiagnosticDTO trouverParId(Long id);

    List<DiagnosticDTO> trouverTous();

    List<DiagnosticDTO> trouverParUtilisateur(Long utilisateurId);

    DiagnosticDTO modifier(Long id, DiagnosticCreateDTO dto);

    void supprimer(Long id);
}