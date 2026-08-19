package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticDTO {

    private Long id;

    private LocalDate dateDiagnostic;

    private String image;

    private Double confiance;

    private Long utilisateurId;

    private Long planteId;
    private String planteNom;

    private Long maladieId;
    private String maladieNom;
}