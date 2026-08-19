package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaladieDTO {

    private Long id;
    private String nom;
    private String symptomes;
    private String traitement;
}