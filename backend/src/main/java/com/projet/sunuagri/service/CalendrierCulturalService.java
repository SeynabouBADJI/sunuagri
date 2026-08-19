package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.CalendrierCulturalCreateDTO;
import com.projet.sunuagri.dto.CalendrierCulturalDTO;

import java.util.List;

public interface CalendrierCulturalService {

    CalendrierCulturalDTO creer(
            CalendrierCulturalCreateDTO dto);

    CalendrierCulturalDTO trouverParId(Long id);

    List<CalendrierCulturalDTO> trouverTous();

    List<CalendrierCulturalDTO> trouverParRegion(
            String region);

    List<CalendrierCulturalDTO> trouverParPlante(
            Long planteId);

    List<CalendrierCulturalDTO> trouverParSaison(
            String saison);

    CalendrierCulturalDTO modifier(
            Long id,
            CalendrierCulturalCreateDTO dto);

    void supprimer(Long id);
}