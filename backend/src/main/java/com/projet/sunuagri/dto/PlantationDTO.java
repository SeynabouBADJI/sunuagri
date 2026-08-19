package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationDTO {

    private Long id;
    private String dateDebut;
    private String dateFin;
    private String saison;

    private Long planteId;
    private String planteNom;

    private Long parcelleId;
    private String parcelleNom;
}