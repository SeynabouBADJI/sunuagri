package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanteCreateDTO {

    @NotBlank(message = "Le nom commun est obligatoire")
    private String nomCommun;

    @NotBlank(message = "Le nom scientifique est obligatoire")
    private String nomScientifique;

    @NotBlank(message = "La famille est obligatoire")
    private String famille;

    @Positive(message = "Le cycle végétatif doit être positif")
    private Integer cycleVegetatif;

    private String description;
}