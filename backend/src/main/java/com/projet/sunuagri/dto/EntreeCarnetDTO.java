package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntreeCarnetDTO {

    private Long id;

    private LocalDate date;

    private String type;

    private String description;

    private String photo;

    private Long parcelleId;
}