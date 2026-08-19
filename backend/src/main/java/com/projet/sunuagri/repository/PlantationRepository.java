package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantationRepository extends JpaRepository<Plantation, Long> {
}