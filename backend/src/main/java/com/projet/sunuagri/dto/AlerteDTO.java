package com.projet.sunuagri.dto;

import com.projet.sunuagri.entity.TypeAlerte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlerteDTO {

    private Long id;

    private String titre;

    private String message;

    private LocalDate dateCreation;

    private TypeAlerte type;

    private String region;

    private Long maladieId;

    private String maladieNom;
}