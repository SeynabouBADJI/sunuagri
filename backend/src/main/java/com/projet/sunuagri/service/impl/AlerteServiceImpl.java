package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.AlerteCreateDTO;
import com.projet.sunuagri.dto.AlerteDTO;
import com.projet.sunuagri.entity.Alerte;
import com.projet.sunuagri.entity.Maladie;
import com.projet.sunuagri.repository.AlerteRepository;
import com.projet.sunuagri.repository.MaladieRepository;
import com.projet.sunuagri.service.AlerteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlerteServiceImpl implements AlerteService {

    private final AlerteRepository alerteRepository;
    private final MaladieRepository maladieRepository;

    @Override
    public AlerteDTO creer(AlerteCreateDTO dto) {

        Alerte alerte = new Alerte();

        alerte.setTitre(dto.getTitre());
        alerte.setMessage(dto.getMessage());

        alerte.setDateCreation(
                dto.getDateCreation() != null
                        ? dto.getDateCreation()
                        : LocalDate.now()
        );

        alerte.setType(dto.getType());
        alerte.setRegion(dto.getRegion());

        if (dto.getMaladieId() != null) {

            Maladie maladie = maladieRepository
                    .findById(dto.getMaladieId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Maladie introuvable"
                            ));

            alerte.setMaladie(maladie);
        }

        return convertirEnDTO(
                alerteRepository.save(alerte)
        );
    }

    @Override
    public AlerteDTO trouverParId(Long id) {

        Alerte alerte = alerteRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alerte introuvable"
                        ));

        return convertirEnDTO(alerte);
    }

    @Override
    public List<AlerteDTO> trouverToutes() {

        return alerteRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<AlerteDTO> trouverParRegion(
            String region) {

        return alerteRepository
                .findByRegion(region)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<AlerteDTO> trouverParMaladie(
            Long maladieId) {

        return alerteRepository
                .findByMaladieId(maladieId)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public AlerteDTO modifier(
            Long id,
            AlerteCreateDTO dto) {

        Alerte alerte = alerteRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alerte introuvable"
                        ));

        alerte.setTitre(dto.getTitre());
        alerte.setMessage(dto.getMessage());
        alerte.setDateCreation(
                dto.getDateCreation() != null
                        ? dto.getDateCreation()
                        : alerte.getDateCreation()
        );
        alerte.setType(dto.getType());
        alerte.setRegion(dto.getRegion());

        if (dto.getMaladieId() != null) {

            Maladie maladie = maladieRepository
                    .findById(dto.getMaladieId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Maladie introuvable"
                            ));

            alerte.setMaladie(maladie);

        } else {
            alerte.setMaladie(null);
        }

        return convertirEnDTO(
                alerteRepository.save(alerte)
        );
    }

    @Override
    public void supprimer(Long id) {

        if (!alerteRepository.existsById(id)) {
            throw new RuntimeException(
                    "Alerte introuvable"
            );
        }

        alerteRepository.deleteById(id);
    }

    private AlerteDTO convertirEnDTO(Alerte alerte) {

        return new AlerteDTO(
                alerte.getId(),
                alerte.getTitre(),
                alerte.getMessage(),
                alerte.getDateCreation(),
                alerte.getType(),
                alerte.getRegion(),

                alerte.getMaladie() != null
                        ? alerte.getMaladie().getId()
                        : null,

                alerte.getMaladie() != null
                        ? alerte.getMaladie().getNom()
                        : null
        );
    }
}