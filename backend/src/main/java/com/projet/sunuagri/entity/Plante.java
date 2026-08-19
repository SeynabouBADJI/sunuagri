package com.projet.sunuagri.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom commun est obligatoire")
    @Column(nullable = false)
    private String nomCommun;

    @NotBlank(message = "Le nom scientifique est obligatoire")
    @Column(nullable = false)
    private String nomScientifique;

    @NotBlank(message = "La famille est obligatoire")
    @Column(nullable = false)
    private String famille;

    @Positive(message = "Le cycle végétatif doit être positif")
    private Integer cycleVegetatif;

    @Column(columnDefinition = "TEXT")
    private String description;
}