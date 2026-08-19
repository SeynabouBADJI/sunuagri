package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.CalendrierCulturalCreateDTO;
import com.projet.sunuagri.dto.CalendrierCulturalDTO;
import com.projet.sunuagri.entity.CalendrierCultural;
import com.projet.sunuagri.entity.Plante;
import com.projet.sunuagri.repository.CalendrierCulturalRepository;
import com.projet.sunuagri.repository.PlanteRepository;
import com.projet.sunuagri.service.CalendrierCulturalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendrierCulturalServiceImpl
        implements CalendrierCulturalService {

    private final CalendrierCulturalRepository calendrierRepository;
    private final PlanteRepository planteRepository;

    @Override
    public CalendrierCulturalDTO creer(
            CalendrierCulturalCreateDTO dto) {

        Plante plante = planteRepository
                .findById(dto.getPlanteId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plante introuvable"));

        CalendrierCultural calendrier =
                new CalendrierCultural();

        calendrier.setRegion(dto.getRegion());
        calendrier.setSaison(dto.getSaison());
        calendrier.setPeriodeSemis(
                dto.getPeriodeSemis());
        calendrier.setPeriodeFloraison(
                dto.getPeriodeFloraison());
        calendrier.setPeriodeRecolte(
                dto.getPeriodeRecolte());
        calendrier.setPlante(plante);

        return convertirEnDTO(
                calendrierRepository.save(calendrier));
    }

    @Override
    public CalendrierCulturalDTO trouverParId(Long id) {

        CalendrierCultural calendrier =
                calendrierRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Calendrier introuvable"));

        return convertirEnDTO(calendrier);
    }

    @Override
    public List<CalendrierCulturalDTO> trouverTous() {

        return calendrierRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<CalendrierCulturalDTO> trouverParRegion(
            String region) {

        return calendrierRepository
                .findByRegion(region)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<CalendrierCulturalDTO> trouverParPlante(
            Long planteId) {

        return calendrierRepository
                .findByPlanteId(planteId)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<CalendrierCulturalDTO> trouverParSaison(
            String saison) {

        return calendrierRepository
                .findBySaison(saison)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public CalendrierCulturalDTO modifier(
            Long id,
            CalendrierCulturalCreateDTO dto) {

        CalendrierCultural calendrier =
                calendrierRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Calendrier introuvable"));

        Plante plante = planteRepository
                .findById(dto.getPlanteId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plante introuvable"));

        calendrier.setRegion(dto.getRegion());
        calendrier.setSaison(dto.getSaison());
        calendrier.setPeriodeSemis(
                dto.getPeriodeSemis());
        calendrier.setPeriodeFloraison(
                dto.getPeriodeFloraison());
        calendrier.setPeriodeRecolte(
                dto.getPeriodeRecolte());
        calendrier.setPlante(plante);

        return convertirEnDTO(
                calendrierRepository.save(calendrier));
    }

    @Override
    public void supprimer(Long id) {

        if (!calendrierRepository.existsById(id)) {
            throw new RuntimeException(
                    "Calendrier introuvable");
        }

        calendrierRepository.deleteById(id);
    }

    private CalendrierCulturalDTO convertirEnDTO(
            CalendrierCultural calendrier) {

        return new CalendrierCulturalDTO(
                calendrier.getId(),
                calendrier.getRegion(),
                calendrier.getSaison(),
                calendrier.getPeriodeSemis(),
                calendrier.getPeriodeFloraison(),
                calendrier.getPeriodeRecolte(),
                calendrier.getPlante().getId(),
                calendrier.getPlante().getNomCommun()
        );
    }
}