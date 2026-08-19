package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntreeCarnetCreateDTO {

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    private String description;

    private String photo;

    @NotNull(message = "La parcelle est obligatoire")
    private Long parcelleId;
}