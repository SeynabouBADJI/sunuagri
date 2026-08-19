package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlerteRepository
        extends JpaRepository<Alerte, Long> {

    List<Alerte> findByRegion(String region);

    List<Alerte> findByMaladieId(Long maladieId);
}