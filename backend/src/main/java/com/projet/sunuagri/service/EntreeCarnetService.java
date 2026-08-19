package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.EntreeCarnetCreateDTO;
import com.projet.sunuagri.dto.EntreeCarnetDTO;

import java.util.List;

public interface EntreeCarnetService {

    EntreeCarnetDTO creer(EntreeCarnetCreateDTO dto);

    EntreeCarnetDTO trouverParId(Long id);

    List<EntreeCarnetDTO> trouverToutes();

    List<EntreeCarnetDTO> trouverParParcelle(Long parcelleId);

    EntreeCarnetDTO modifier(
            Long id,
            EntreeCarnetCreateDTO dto
    );

    void supprimer(Long id);
}