package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendrierCulturalCreateDTO {

    @NotBlank(message = "La région est obligatoire")
    private String region;

    @NotBlank(message = "La saison est obligatoire")
    private String saison;

    private String periodeSemis;

    private String periodeFloraison;

    private String periodeRecolte;

    @NotNull(message = "La plante est obligatoire")
    private Long planteId;
}