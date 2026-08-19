package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendrierCulturalDTO {

    private Long id;

    private String region;

    private String saison;

    private String periodeSemis;

    private String periodeFloraison;

    private String periodeRecolte;

    private Long planteId;

    private String planteNom;
}