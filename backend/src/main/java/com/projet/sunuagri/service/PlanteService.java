package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.PlanteCreateDTO;
import com.projet.sunuagri.dto.PlanteDTO;

import java.util.List;

public interface PlanteService {

    PlanteDTO creer(PlanteCreateDTO dto);

    PlanteDTO trouverParId(Long id);

    List<PlanteDTO> trouverToutes();

    PlanteDTO modifier(Long id, PlanteCreateDTO dto);

    void supprimer(Long id);
}