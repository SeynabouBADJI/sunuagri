package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.AlerteCreateDTO;
import com.projet.sunuagri.dto.AlerteDTO;

import java.util.List;

public interface AlerteService {

    AlerteDTO creer(AlerteCreateDTO dto);

    AlerteDTO trouverParId(Long id);

    List<AlerteDTO> trouverToutes();

    List<AlerteDTO> trouverParRegion(String region);

    List<AlerteDTO> trouverParMaladie(Long maladieId);

    AlerteDTO modifier(Long id, AlerteCreateDTO dto);

    void supprimer(Long id);
}