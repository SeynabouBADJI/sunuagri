package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatistiquesDTO {

    private long totalUtilisateurs;

    private long totalAgriculteurs;

    private long totalAdministrateurs;

    private long totalPlantes;

    private long totalCalendriers;
}