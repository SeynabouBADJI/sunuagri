package com.projet.sunuagri.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "calendriers_culturaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendrierCultural {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Plante concernée par ce calendrier.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plante_id", nullable = false)
    private Plante plante;

    /**
     * Zone agricole concernée.
     * Exemple : Kaolack, Kaffrine, Thiès, Saint-Louis...
     */
    @Column(name = "zone_agricole", nullable = false)
    private String zoneAgricole;

    @Column(name = "duree_cycle")
    private String dureeCycle;

    /**
     * Période recommandée pour le semis.
     */
    @Column(name = "periode_semis")
    private String periodeSemis;

    /**
     * Période estimée pour la récolte.
     */
    @Column(name = "periode_recolte")
    private String periodeRecolte;

    /**
     * Conditions particulières liées à la culture.
     */
    @Column(columnDefinition = "TEXT")
    private String conditions;

    /**
     * Principaux risques liés aux variations climatiques.
     */
    @Column(name = "risques_climatiques", columnDefinition = "TEXT")
    private String risquesClimatiques;

    /**
     * Mesures permettant de réduire les impacts
     * du changement climatique.
     */
    @Column(name = "mesures_adaptation", columnDefinition = "TEXT")
    private String mesuresAdaptation;
}