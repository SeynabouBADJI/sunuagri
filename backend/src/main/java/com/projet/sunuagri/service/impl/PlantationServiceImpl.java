package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.PlantationCreateDTO;
import com.projet.sunuagri.dto.PlantationDTO;
import com.projet.sunuagri.entity.Parcelle;
import com.projet.sunuagri.entity.Plante;
import com.projet.sunuagri.entity.Plantation;
import com.projet.sunuagri.repository.ParcelleRepository;
import com.projet.sunuagri.repository.PlanteRepository;
import com.projet.sunuagri.repository.PlantationRepository;
import com.projet.sunuagri.service.PlantationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantationServiceImpl implements PlantationService {

    private final PlantationRepository plantationRepository;
    private final PlanteRepository planteRepository;
    private final ParcelleRepository parcelleRepository;

    @Override
    public PlantationDTO creer(PlantationCreateDTO dto) {

        Plante plante = planteRepository.findById(dto.getPlanteId())
                .orElseThrow(() ->
                        new RuntimeException("Plante introuvable"));

        Parcelle parcelle = parcelleRepository.findById(dto.getParcelleId())
                .orElseThrow(() ->
                        new RuntimeException("Parcelle introuvable"));

        Plantation plantation = new Plantation();

        plantation.setDateDebut(dto.getDateDebut());
        plantation.setDateFin(dto.getDateFin());
        plantation.setSaison(dto.getSaison());
        plantation.setPlante(plante);
        plantation.setParcelle(parcelle);

        return convertirEnDTO(
                plantationRepository.save(plantation)
        );
    }

    @Override
    public PlantationDTO trouverParId(Long id) {

        Plantation plantation = plantationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Plantation introuvable"));

        return convertirEnDTO(plantation);
    }

    @Override
    public List<PlantationDTO> trouverToutes() {

        return plantationRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public PlantationDTO modifier(
            Long id,
            PlantationCreateDTO dto) {

        Plantation plantation = plantationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Plantation introuvable"));

        Plante plante = planteRepository.findById(dto.getPlanteId())
                .orElseThrow(() ->
                        new RuntimeException("Plante introuvable"));

        Parcelle parcelle = parcelleRepository.findById(dto.getParcelleId())
                .orElseThrow(() ->
                        new RuntimeException("Parcelle introuvable"));

        plantation.setDateDebut(dto.getDateDebut());
        plantation.setDateFin(dto.getDateFin());
        plantation.setSaison(dto.getSaison());
        plantation.setPlante(plante);
        plantation.setParcelle(parcelle);

        return convertirEnDTO(
                plantationRepository.save(plantation)
        );
    }

    @Override
    public void supprimer(Long id) {

        if (!plantationRepository.existsById(id)) {
            throw new RuntimeException("Plantation introuvable");
        }

        plantationRepository.deleteById(id);
    }

    private PlantationDTO convertirEnDTO(Plantation plantation) {

        return new PlantationDTO(
                plantation.getId(),
                plantation.getDateDebut(),
                plantation.getDateFin(),
                plantation.getSaison(),

                plantation.getPlante().getId(),
                plantation.getPlante().getNomCommun(),

                plantation.getParcelle().getId(),
                plantation.getParcelle().getNom()
        );
    }
}