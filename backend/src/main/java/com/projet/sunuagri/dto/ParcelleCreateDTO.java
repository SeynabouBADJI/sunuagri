package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ParcelleCreateDTO {
    @NotBlank
    private String nom;

    @Positive
    private Double superficie;

    @NotBlank
    private String localisation;

    private String notes;

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long utilisateurId;
}