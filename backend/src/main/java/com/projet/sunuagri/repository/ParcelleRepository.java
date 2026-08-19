package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Parcelle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParcelleRepository extends JpaRepository<Parcelle, Long> {
    List<Parcelle> findByUtilisateurId(Long utilisateurId);
}