package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.UtilisateurCreateDTO;
import com.projet.sunuagri.dto.UtilisateurDTO;
import com.projet.sunuagri.dto.LoginDTO;
import java.util.List;

public interface UtilisateurService {
    List<UtilisateurDTO> getTousLesUtilisateurs();
    UtilisateurDTO getUtilisateurParId(Long id);
    UtilisateurDTO creerUtilisateur(UtilisateurCreateDTO dto);
    UtilisateurDTO modifierUtilisateur(Long id, UtilisateurCreateDTO dto);
    UtilisateurDTO authentifier(LoginDTO dto);
    void supprimerUtilisateur(Long id);
}