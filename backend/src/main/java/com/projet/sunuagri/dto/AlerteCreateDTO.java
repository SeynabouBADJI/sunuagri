package com.projet.sunuagri.dto;

import com.projet.sunuagri.entity.TypeAlerte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlerteCreateDTO {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String message;

    private LocalDate dateCreation;

    @NotNull(message = "Le type est obligatoire")
    private TypeAlerte type;

    private String region;

    private Long maladieId;
}