package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.DiagnosticCreateDTO;
import com.projet.sunuagri.dto.DiagnosticDTO;
import com.projet.sunuagri.entity.Diagnostic;
import com.projet.sunuagri.entity.Maladie;
import com.projet.sunuagri.entity.Plante;
import com.projet.sunuagri.entity.Utilisateur;
import com.projet.sunuagri.repository.DiagnosticRepository;
import com.projet.sunuagri.repository.MaladieRepository;
import com.projet.sunuagri.repository.PlanteRepository;
import com.projet.sunuagri.repository.UtilisateurRepository;
import com.projet.sunuagri.service.DiagnosticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosticServiceImpl implements DiagnosticService {

    private final DiagnosticRepository diagnosticRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PlanteRepository planteRepository;
    private final MaladieRepository maladieRepository;

    @Override
    public DiagnosticDTO creer(DiagnosticCreateDTO dto) {

        Utilisateur utilisateur = utilisateurRepository
                .findById(dto.getUtilisateurId())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable"));

        Diagnostic diagnostic = new Diagnostic();

        diagnostic.setDateDiagnostic(
                dto.getDateDiagnostic() != null
                        ? dto.getDateDiagnostic()
                        : LocalDate.now()
        );

        diagnostic.setImage(dto.getImage());
        diagnostic.setConfiance(dto.getConfiance());
        diagnostic.setUtilisateur(utilisateur);

        if (dto.getPlanteId() != null) {

            Plante plante = planteRepository
                    .findById(dto.getPlanteId())
                    .orElseThrow(() ->
                            new RuntimeException("Plante introuvable"));

            diagnostic.setPlante(plante);
        }

        if (dto.getMaladieId() != null) {

            Maladie maladie = maladieRepository
                    .findById(dto.getMaladieId())
                    .orElseThrow(() ->
                            new RuntimeException("Maladie introuvable"));

            diagnostic.setMaladie(maladie);
        }

        return convertirEnDTO(
                diagnosticRepository.save(diagnostic)
        );
    }

    @Override
    public DiagnosticDTO trouverParId(Long id) {

        Diagnostic diagnostic = diagnosticRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Diagnostic introuvable"));

        return convertirEnDTO(diagnostic);
    }

    @Override
    public List<DiagnosticDTO> trouverTous() {

        return diagnosticRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<DiagnosticDTO> trouverParUtilisateur(
            Long utilisateurId) {

        return diagnosticRepository
                .findByUtilisateurId(utilisateurId)
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public DiagnosticDTO modifier(
            Long id,
            DiagnosticCreateDTO dto) {

        Diagnostic diagnostic = diagnosticRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Diagnostic introuvable"));

        Utilisateur utilisateur = utilisateurRepository
                .findById(dto.getUtilisateurId())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable"));

        diagnostic.setDateDiagnostic(
                dto.getDateDiagnostic() != null
                        ? dto.getDateDiagnostic()
                        : diagnostic.getDateDiagnostic()
        );

        diagnostic.setImage(dto.getImage());
        diagnostic.setConfiance(dto.getConfiance());
        diagnostic.setUtilisateur(utilisateur);

        if (dto.getPlanteId() != null) {

            Plante plante = planteRepository
                    .findById(dto.getPlanteId())
                    .orElseThrow(() ->
                            new RuntimeException("Plante introuvable"));

            diagnostic.setPlante(plante);

        } else {
            diagnostic.setPlante(null);
        }

        if (dto.getMaladieId() != null) {

            Maladie maladie = maladieRepository
                    .findById(dto.getMaladieId())
                    .orElseThrow(() ->
                            new RuntimeException("Maladie introuvable"));

            diagnostic.setMaladie(maladie);

        } else {
            diagnostic.setMaladie(null);
        }

        return convertirEnDTO(
                diagnosticRepository.save(diagnostic)
        );
    }

    @Override
    public void supprimer(Long id) {

        if (!diagnosticRepository.existsById(id)) {
            throw new RuntimeException(
                    "Diagnostic introuvable"
            );
        }

        diagnosticRepository.deleteById(id);
    }

    private DiagnosticDTO convertirEnDTO(
            Diagnostic diagnostic) {

        return new DiagnosticDTO(
                diagnostic.getId(),
                diagnostic.getDateDiagnostic(),
                diagnostic.getImage(),
                diagnostic.getConfiance(),

                diagnostic.getUtilisateur().getId(),

                diagnostic.getPlante() != null
                        ? diagnostic.getPlante().getId()
                        : null,

                diagnostic.getPlante() != null
                        ? diagnostic.getPlante().getNomCommun()
                        : null,

                diagnostic.getMaladie() != null
                        ? diagnostic.getMaladie().getId()
                        : null,

                diagnostic.getMaladie() != null
                        ? diagnostic.getMaladie().getNom()
                        : null
        );
    }
}