package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.EntreeCarnet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntreeCarnetRepository
        extends JpaRepository<EntreeCarnet, Long> {

    List<EntreeCarnet> findByParcelleId(Long parcelleId);
}