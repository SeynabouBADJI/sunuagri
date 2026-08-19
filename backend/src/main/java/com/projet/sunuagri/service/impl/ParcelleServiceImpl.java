package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.ParcelleCreateDTO;
import com.projet.sunuagri.dto.ParcelleDTO;
import com.projet.sunuagri.entity.Parcelle;
import com.projet.sunuagri.entity.Utilisateur;
import com.projet.sunuagri.repository.ParcelleRepository;
import com.projet.sunuagri.repository.UtilisateurRepository;
import com.projet.sunuagri.service.ParcelleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcelleServiceImpl implements ParcelleService {

    private final ParcelleRepository parcelleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ParcelleServiceImpl(ParcelleRepository parcelleRepository, UtilisateurRepository utilisateurRepository) {
        this.parcelleRepository = parcelleRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<ParcelleDTO> getParcellesParUtilisateur(Long utilisateurId) {
        return parcelleRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .map(ParcelleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ParcelleDTO getParcelleParId(Long id) {
        Parcelle parcelle = parcelleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcelle introuvable avec l'id " + id));
        return ParcelleDTO.fromEntity(parcelle);
    }

    @Override
    public ParcelleDTO creerParcelle(ParcelleCreateDTO dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id " + dto.getUtilisateurId()));

        Parcelle parcelle = new Parcelle();
        parcelle.setNom(dto.getNom());
        parcelle.setSuperficie(dto.getSuperficie());
        parcelle.setLocalisation(dto.getLocalisation());
        parcelle.setNotes(dto.getNotes());
        parcelle.setUtilisateur(utilisateur);

        Parcelle enregistree = parcelleRepository.save(parcelle);
        return ParcelleDTO.fromEntity(enregistree);
    }

    @Override
    public ParcelleDTO modifierParcelle(Long id, ParcelleCreateDTO dto) {
        Parcelle parcelle = parcelleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcelle introuvable avec l'id " + id));

        parcelle.setNom(dto.getNom());
        parcelle.setSuperficie(dto.getSuperficie());
        parcelle.setLocalisation(dto.getLocalisation());
        parcelle.setNotes(dto.getNotes());

        Parcelle miseAJour = parcelleRepository.save(parcelle);
        return ParcelleDTO.fromEntity(miseAJour);
    }

    @Override
    public void supprimerParcelle(Long id) {
        if (!parcelleRepository.existsById(id)) {
            throw new RuntimeException("Parcelle introuvable avec l'id " + id);
        }
        parcelleRepository.deleteById(id);
    }
}