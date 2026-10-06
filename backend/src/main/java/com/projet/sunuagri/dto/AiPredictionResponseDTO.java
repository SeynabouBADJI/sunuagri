package com.projet.sunuagri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AiPredictionResponseDTO {

    private boolean success;

    private MaladieInfo maladie;

    private Double confiance;

    private List<Top3Item> top3;

    private String message;

    @Data
    public static class MaladieInfo {
        private String code;

        @JsonProperty("nom_fr")
        private String nomFr;

        private String culture;

        private boolean saine;
    }

    @Data
    public static class Top3Item {
        private String code;

        @JsonProperty("nom_fr")
        private String nomFr;

        private Double confiance;
    }
}