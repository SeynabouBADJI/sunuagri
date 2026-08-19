package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationCreateDTO {

    private String dateDebut;

    private String dateFin;

    @NotBlank(message = "La saison est obligatoire")
    private String saison;

    @NotNull(message = "La plante est obligatoire")
    private Long planteId;

    @NotNull(message = "La parcelle est obligatoire")
    private Long parcelleId;
}
