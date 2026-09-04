package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.CalendrierCulturalCreateDTO;
import com.projet.sunuagri.dto.CalendrierCulturalDTO;

import java.util.List;

public interface CalendrierCulturalService {

    CalendrierCulturalDTO creer(CalendrierCulturalCreateDTO dto);

    CalendrierCulturalDTO trouverParId(Long id);

    List<CalendrierCulturalDTO> trouverToutes();

    List<CalendrierCulturalDTO> trouverParPlante(Long planteId);

    List<CalendrierCulturalDTO> trouverParZone(String zoneAgricole);

    CalendrierCulturalDTO modifier(
            Long id,
            CalendrierCulturalCreateDTO dto
    );

    void supprimer(Long id);
}