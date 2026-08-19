package com.projet.sunuagri.dto;

import com.projet.sunuagri.entity.Parcelle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelleDTO {
    private Long id;
    private String nom;
    private Double superficie;
    private String localisation;
    private String notes;
    private Long utilisateurId;

    public static ParcelleDTO fromEntity(Parcelle p) {
        return new ParcelleDTO(
            p.getId(), p.getNom(), p.getSuperficie(),
            p.getLocalisation(), p.getNotes(),
            p.getUtilisateur().getId()
        );
    }
}