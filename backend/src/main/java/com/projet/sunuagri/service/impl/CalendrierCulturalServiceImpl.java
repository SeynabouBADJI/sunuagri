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

        Plante plante = planteRepository.findById(dto.getPlanteId())
                .orElseThrow(() ->
                        new RuntimeException("Plante introuvable"));

        CalendrierCultural calendrier = new CalendrierCultural();

        calendrier.setPlante(plante);
        calendrier.setZoneAgricole(dto.getZoneAgricole());
        calendrier.setDureeCycle(dto.getDureeCycle());
        calendrier.setPeriodeSemis(dto.getPeriodeSemis());
        calendrier.setPeriodeRecolte(dto.getPeriodeRecolte());
        calendrier.setConditions(dto.getConditions());
        calendrier.setRisquesClimatiques(dto.getRisquesClimatiques());
        calendrier.setMesuresAdaptation(dto.getMesuresAdaptation());

        return convertirEnDTO(
                calendrierRepository.save(calendrier)
        );
    }

    @Override
    public CalendrierCulturalDTO trouverParId(Long id) {

        CalendrierCultural calendrier =
                calendrierRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Calendrier introuvable"
                                ));

        return convertirEnDTO(calendrier);
    }

    @Override
    public List<CalendrierCulturalDTO> trouverToutes() {

        return calendrierRepository.findAll()
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
    public List<CalendrierCulturalDTO> trouverParZone(
            String zoneAgricole) {

        return calendrierRepository
                .findByZoneAgricoleIgnoreCase(zoneAgricole)
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
                                        "Calendrier introuvable"
                                ));

        Plante plante =
                planteRepository.findById(dto.getPlanteId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Plante introuvable"
                                ));

        calendrier.setPlante(plante);
        calendrier.setZoneAgricole(dto.getZoneAgricole());
        calendrier.setDureeCycle(dto.getDureeCycle());
        calendrier.setPeriodeSemis(dto.getPeriodeSemis());
        calendrier.setPeriodeRecolte(dto.getPeriodeRecolte());
        calendrier.setConditions(dto.getConditions());
        calendrier.setRisquesClimatiques(dto.getRisquesClimatiques());
        calendrier.setMesuresAdaptation(dto.getMesuresAdaptation());

        return convertirEnDTO(
                calendrierRepository.save(calendrier)
        );
    }

    @Override
    public void supprimer(Long id) {

        if (!calendrierRepository.existsById(id)) {
            throw new RuntimeException(
                    "Calendrier introuvable"
            );
        }

        calendrierRepository.deleteById(id);
    }

    private CalendrierCulturalDTO convertirEnDTO(
            CalendrierCultural calendrier) {

        return new CalendrierCulturalDTO(
                calendrier.getId(),
                calendrier.getPlante().getId(),
                calendrier.getPlante().getNomCommun(),
                calendrier.getZoneAgricole(),
                calendrier.getDureeCycle(),
                calendrier.getPeriodeSemis(),
                calendrier.getPeriodeRecolte(),
                calendrier.getConditions(),
                calendrier.getRisquesClimatiques(),
                calendrier.getMesuresAdaptation()
        );
    }
}