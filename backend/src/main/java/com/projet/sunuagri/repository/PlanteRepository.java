package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Plante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanteRepository extends JpaRepository<Plante, Long> {
}