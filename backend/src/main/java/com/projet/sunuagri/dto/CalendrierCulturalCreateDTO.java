package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendrierCulturalCreateDTO {

    @NotNull(message = "La plante est obligatoire")
    private Long planteId;

    @NotNull(message = "La zone agricole est obligatoire")
    private String zoneAgricole;

    private String dureeCycle;

    private String periodeSemis;

    private String periodeRecolte;

    private String conditions;

    private String risquesClimatiques;

    private String mesuresAdaptation;
}