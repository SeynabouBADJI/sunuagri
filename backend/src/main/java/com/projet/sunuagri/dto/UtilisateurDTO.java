package com.projet.sunuagri.dto;

import com.projet.sunuagri.entity.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Utilisateur.Role role;
    private String localisation;

    public static UtilisateurDTO fromEntity(Utilisateur u) {
        return new UtilisateurDTO(
            u.getId(), u.getNom(), u.getPrenom(), u.getEmail(),
            u.getTelephone(), u.getRole(), u.getLocalisation()
        );
    }
}