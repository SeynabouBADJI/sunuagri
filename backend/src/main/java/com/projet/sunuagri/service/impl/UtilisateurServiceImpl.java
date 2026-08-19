package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.service.UtilisateurService;
import com.projet.sunuagri.dto.UtilisateurCreateDTO;
import com.projet.sunuagri.dto.UtilisateurDTO;
import com.projet.sunuagri.dto.LoginDTO;
import com.projet.sunuagri.entity.Utilisateur;
import com.projet.sunuagri.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<UtilisateurDTO> getTousLesUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO getUtilisateurParId(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id " + id));
        return UtilisateurDTO.fromEntity(utilisateur);
    }

    @Override
    public UtilisateurDTO creerUtilisateur(UtilisateurCreateDTO dto) {
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe deja");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setMotDePasse(dto.getMotDePasse());
        utilisateur.setLocalisation(dto.getLocalisation());
        utilisateur.setRole(Utilisateur.Role.AGRICULTEUR);

        Utilisateur enregistre = utilisateurRepository.save(utilisateur);
        return UtilisateurDTO.fromEntity(enregistre);
    }

    @Override
    public UtilisateurDTO modifierUtilisateur(Long id, UtilisateurCreateDTO dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id " + id));

        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setLocalisation(dto.getLocalisation());

        Utilisateur miseAJour = utilisateurRepository.save(utilisateur);
        return UtilisateurDTO.fromEntity(miseAJour);
    }

    @Override
    public void supprimerUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable avec l'id " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDTO authentifier(LoginDTO dto) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!utilisateur.getMotDePasse().equals(dto.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        return UtilisateurDTO.fromEntity(utilisateur);
    }
}