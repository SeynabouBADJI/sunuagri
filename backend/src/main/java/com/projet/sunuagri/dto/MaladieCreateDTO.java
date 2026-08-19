package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaladieCreateDTO {

    @NotBlank(message = "Le nom de la maladie est obligatoire")
    private String nom;

    private String symptomes;

    private String traitement;
}