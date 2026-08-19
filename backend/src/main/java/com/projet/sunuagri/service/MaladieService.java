package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.MaladieCreateDTO;
import com.projet.sunuagri.dto.MaladieDTO;

import java.util.List;

public interface MaladieService {

    MaladieDTO creer(MaladieCreateDTO dto);

    MaladieDTO trouverParId(Long id);

    List<MaladieDTO> trouverToutes();

    MaladieDTO modifier(Long id, MaladieCreateDTO dto);

    void supprimer(Long id);
}