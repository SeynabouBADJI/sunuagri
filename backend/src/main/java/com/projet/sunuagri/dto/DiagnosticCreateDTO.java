package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticCreateDTO {

    private LocalDate dateDiagnostic;

    private String image;

    private Double confiance;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long utilisateurId;

    private Long planteId;

    private Long maladieId;
}