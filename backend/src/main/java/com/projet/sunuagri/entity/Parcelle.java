package com.projet.sunuagri.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "parcelles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la parcelle est obligatoire")
    private String nom;

    @Positive(message = "La superficie doit etre positive")
    private Double superficie;

    @NotBlank(message = "La localisation est obligatoire")
    private String localisation;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
}