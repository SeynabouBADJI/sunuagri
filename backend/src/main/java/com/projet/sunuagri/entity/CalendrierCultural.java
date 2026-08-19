package com.projet.sunuagri.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "calendriers_culturaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendrierCultural {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String saison;

    private String periodeSemis;

    private String periodeFloraison;

    private String periodeRecolte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plante_id", nullable = false)
    private Plante plante;
}