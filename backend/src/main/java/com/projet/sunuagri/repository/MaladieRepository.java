package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;   // ← AJOUT


public interface MaladieRepository extends JpaRepository<Maladie, Long> {
        Optional<Maladie> findByNom(String nom);

}