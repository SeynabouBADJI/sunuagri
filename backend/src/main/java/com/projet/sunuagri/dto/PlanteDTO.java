package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanteDTO {

    private Long id;
    private String nomCommun;
    private String nomScientifique;
    private String famille;
    private Integer cycleVegetatif;
    private String description;
}