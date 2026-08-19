package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.PlanteCreateDTO;
import com.projet.sunuagri.dto.PlanteDTO;
import com.projet.sunuagri.entity.Plante;
import com.projet.sunuagri.repository.PlanteRepository;
import com.projet.sunuagri.service.PlanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanteServiceImpl implements PlanteService {

    private final PlanteRepository planteRepository;

    @Override
    public PlanteDTO creer(PlanteCreateDTO dto) {

        Plante plante = new Plante();

        plante.setNomCommun(dto.getNomCommun());
        plante.setNomScientifique(dto.getNomScientifique());
        plante.setFamille(dto.getFamille());
        plante.setCycleVegetatif(dto.getCycleVegetatif());
        plante.setDescription(dto.getDescription());

        Plante planteEnregistree = planteRepository.save(plante);

        return convertirEnDTO(planteEnregistree);
    }

    @Override
    public PlanteDTO trouverParId(Long id) {

        Plante plante = planteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Plante introuvable avec l'id : " + id));

        return convertirEnDTO(plante);
    }

    @Override
    public List<PlanteDTO> trouverToutes() {

        return planteRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public PlanteDTO modifier(Long id, PlanteCreateDTO dto) {

        Plante plante = planteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Plante introuvable avec l'id : " + id));

        plante.setNomCommun(dto.getNomCommun());
        plante.setNomScientifique(dto.getNomScientifique());
        plante.setFamille(dto.getFamille());
        plante.setCycleVegetatif(dto.getCycleVegetatif());
        plante.setDescription(dto.getDescription());

        return convertirEnDTO(planteRepository.save(plante));
    }

    @Override
    public void supprimer(Long id) {

        if (!planteRepository.existsById(id)) {
            throw new RuntimeException(
                    "Plante introuvable avec l'id : " + id);
        }

        planteRepository.deleteById(id);
    }

    private PlanteDTO convertirEnDTO(Plante plante) {

        return new PlanteDTO(
                plante.getId(),
                plante.getNomCommun(),
                plante.getNomScientifique(),
                plante.getFamille(),
                plante.getCycleVegetatif(),
                plante.getDescription()
        );
    }
}