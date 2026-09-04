package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendrierCulturalDTO {

    private Long id;

    private Long planteId;

    private String nomPlante;

    private String zoneAgricole;

    private String dureeCycle;

    private String periodeSemis;

    private String periodeRecolte;

    private String conditions;

    private String risquesClimatiques;

    private String mesuresAdaptation;
}