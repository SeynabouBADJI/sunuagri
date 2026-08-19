package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.PlantationCreateDTO;
import com.projet.sunuagri.dto.PlantationDTO;

import java.util.List;

public interface PlantationService {

    PlantationDTO creer(PlantationCreateDTO dto);

    PlantationDTO trouverParId(Long id);

    List<PlantationDTO> trouverToutes();

    PlantationDTO modifier(Long id, PlantationCreateDTO dto);

    void supprimer(Long id);
}