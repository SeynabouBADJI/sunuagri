package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosticRepository
        extends JpaRepository<Diagnostic, Long> {

    List<Diagnostic> findByUtilisateurId(Long utilisateurId);
}