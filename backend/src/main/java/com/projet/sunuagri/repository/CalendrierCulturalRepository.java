package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.CalendrierCultural;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendrierCulturalRepository
        extends JpaRepository<CalendrierCultural, Long> {

    List<CalendrierCultural> findByPlanteId(Long planteId);

    List<CalendrierCultural> findByZoneAgricoleIgnoreCase(
            String zoneAgricole
    );
}