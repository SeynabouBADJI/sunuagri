package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.ParcelleCreateDTO;
import com.projet.sunuagri.dto.ParcelleDTO;
import java.util.List;

public interface ParcelleService {
    List<ParcelleDTO> getParcellesParUtilisateur(Long utilisateurId);
    ParcelleDTO getParcelleParId(Long id);
    ParcelleDTO creerParcelle(ParcelleCreateDTO dto);
    ParcelleDTO modifierParcelle(Long id, ParcelleCreateDTO dto);
    void supprimerParcelle(Long id);
}