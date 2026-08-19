package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaladieRepository extends JpaRepository<Maladie, Long> {
}