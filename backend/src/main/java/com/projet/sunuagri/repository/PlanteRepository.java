package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Plante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;   // ← AJOUT


public interface PlanteRepository extends JpaRepository<Plante, Long> {

    boolean existsByNomCommunIgnoreCase(String nomCommun);
        Optional<Plante> findByNomCommun(String nomCommun);

}