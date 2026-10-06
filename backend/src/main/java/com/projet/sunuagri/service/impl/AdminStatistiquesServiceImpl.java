package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.AdminStatistiquesDTO;
import com.projet.sunuagri.entity.Utilisateur;
import com.projet.sunuagri.repository.CalendrierCulturalRepository;
import com.projet.sunuagri.repository.PlanteRepository;
import com.projet.sunuagri.repository.UtilisateurRepository;
import com.projet.sunuagri.service.AdminStatistiquesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminStatistiquesServiceImpl
        implements AdminStatistiquesService {

    private final UtilisateurRepository utilisateurRepository;
    private final PlanteRepository planteRepository;
    private final CalendrierCulturalRepository calendrierCulturalRepository;

    @Override
    public AdminStatistiquesDTO obtenirStatistiques() {

        long totalUtilisateurs =
                utilisateurRepository.count();

        long totalAgriculteurs =
                utilisateurRepository.countByRole(
                        Utilisateur.Role.AGRICULTEUR
                );

        long totalAdministrateurs =
                utilisateurRepository.countByRole(
                        Utilisateur.Role.ADMINISTRATEUR
                );

        long totalPlantes =
                planteRepository.count();

        long totalCalendriers =
                calendrierCulturalRepository.count();

        return new AdminStatistiquesDTO(
                totalUtilisateurs,
                totalAgriculteurs,
                totalAdministrateurs,
                totalPlantes,
                totalCalendriers
        );
    }
}