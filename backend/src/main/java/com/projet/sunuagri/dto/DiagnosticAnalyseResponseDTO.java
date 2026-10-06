package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticAnalyseResponseDTO {

    // Diagnostic enregistré
    private Long diagnosticId;
    private LocalDate dateDiagnostic;

    // Résultat IA
    private String maladieCode;        // "Tomato___Late_blight"
    private String maladieNom;         // "Mildiou (tomate)"
    private String culture;            // "Tomate"
    private boolean saine;             // false
    private Double confiance;          // 99.96

    // Infos BDD sur la maladie
    private Long maladieId;
    private String symptomes;
    private String traitement;

    // Top 3 prédictions
    private List<Top3Item> top3;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Top3Item {
        private String code;
        private String nomFr;
        private Double confiance;
    }
}