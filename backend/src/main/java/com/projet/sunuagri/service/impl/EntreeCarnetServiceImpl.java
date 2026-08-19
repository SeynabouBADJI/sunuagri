package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.EntreeCarnetCreateDTO;
import com.projet.sunuagri.dto.EntreeCarnetDTO;
import com.projet.sunuagri.entity.EntreeCarnet;
import com.projet.sunuagri.entity.Parcelle;
import com.projet.sunuagri.repository.EntreeCarnetRepository;
import com.projet.sunuagri.repository.ParcelleRepository;
import com.projet.sunuagri.service.EntreeCarnetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntreeCarnetServiceImpl implements EntreeCarnetService {

    private final EntreeCarnetRepository entreeCarnetRepository;
    private final ParcelleRepository parcelleRepository;

    @Override
    public EntreeCarnetDTO creer(EntreeCarnetCreateDTO dto) {

        Parcelle parcelle = parcelleRepository
                .findById(dto.getParcelleId())
                .orElseThrow(() ->
                        new RuntimeException("Parcelle introuvable"));

        EntreeCarnet entree = new EntreeCarnet();

        entree.setDate(dto.getDate());
        entree.setType(dto.getType());
        entree.setDescription(dto.getDescription());
        entree.setPhoto(dto.getPhoto());
        entree.setParcelle(parcelle);

        return convertirEnDTO(
                entreeCarnetRepository.save(entree)
        );
    }

    @Override
    public EntreeCarnetDTO trouverParId(Long id) {

        EntreeCarnet entree = entreeCarnetRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Entrée du carnet introuvable"));

        return convertirEnDTO(entree);
    }

    @Override
    public List<EntreeCarnetDTO> trouverToutes() {

        return entreeCarnetRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<EntreeCarnetDTO> trouverParParcelle(
            Long parcelleId) {

        return entreeCarnetRepository
                .findByParcelleId(parcelleId)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public EntreeCarnetDTO modifier(
            Long id,
            EntreeCarnetCreateDTO dto) {

        EntreeCarnet entree = entreeCarnetRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Entrée du carnet introuvable"));

        Parcelle parcelle = parcelleRepository
                .findById(dto.getParcelleId())
                .orElseThrow(() ->
                        new RuntimeException("Parcelle introuvable"));

        entree.setDate(dto.getDate());
        entree.setType(dto.getType());
        entree.setDescription(dto.getDescription());
        entree.setPhoto(dto.getPhoto());
        entree.setParcelle(parcelle);

        return convertirEnDTO(
                entreeCarnetRepository.save(entree)
        );
    }

    @Override
    public void supprimer(Long id) {

        if (!entreeCarnetRepository.existsById(id)) {
            throw new RuntimeException(
                    "Entrée du carnet introuvable"
            );
        }

        entreeCarnetRepository.deleteById(id);
    }

    private EntreeCarnetDTO convertirEnDTO(
            EntreeCarnet entree) {

        return new EntreeCarnetDTO(
                entree.getId(),
                entree.getDate(),
                entree.getType(),
                entree.getDescription(),
                entree.getPhoto(),
                entree.getParcelle().getId()
        );
    }
}