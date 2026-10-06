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
import com.projet.sunuagri.dto.DiagnosticAnalyseResponseDTO;   // ← AJOUT
import org.springframework.web.multipart.MultipartFile;        // ← AJOUT

import com.projet.sunuagri.service.AiService;                  // ← AJOUT

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosticServiceImpl implements DiagnosticService {

    private final DiagnosticRepository diagnosticRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PlanteRepository planteRepository;
    private final MaladieRepository maladieRepository;
    private final AiService aiService;   // ← AJOUT


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

    @Override
    public DiagnosticAnalyseResponseDTO analyser(
            MultipartFile image,
            Long utilisateurId) {

        // 1. Vérifier l'utilisateur
        Utilisateur utilisateur = utilisateurRepository
                .findById(utilisateurId)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable"));

        // 2. Appeler le service IA (FastAPI)
        AiPredictionResponseDTO iaResult = aiService.analyserImage(image);

        if (iaResult == null || iaResult.getMaladie() == null) {
                throw new RuntimeException("Réponse IA invalide");
        }

        String codeMaladie = iaResult.getMaladie().getCode();
        String nomFr = iaResult.getMaladie().getNomFr();
        String culture = iaResult.getMaladie().getCulture();
        Double confiance = iaResult.getConfiance();

        // 3. Chercher la maladie en BDD (par nom ou code)
        //    ⚠️ La BDD doit contenir les 12 maladies avec les mêmes noms
        Maladie maladie = maladieRepository
                .findByNom(nomFr)
                .orElse(null);   // null si pas trouvée → on enregistre quand même

        // 4. Chercher la plante correspondante
        Plante plante = planteRepository
                .findByNomCommun(culture)
                .orElse(null);

        // 5. Enregistrer le diagnostic
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setDateDiagnostic(LocalDate.now());
        diagnostic.setImage(image.getOriginalFilename());
         diagnostic.setConfiance(confiance != null ? confiance / 100.0 : null);
        diagnostic.setUtilisateur(utilisateur);
        diagnostic.setPlante(plante);
        diagnostic.setMaladie(maladie);

        Diagnostic saved = diagnosticRepository.save(diagnostic);

        // 6. Construire la réponse complète
        DiagnosticAnalyseResponseDTO response = new DiagnosticAnalyseResponseDTO();

        response.setDiagnosticId(saved.getId());
        response.setDateDiagnostic(saved.getDateDiagnostic());
        response.setMaladieCode(codeMaladie);
        response.setMaladieNom(nomFr);
        response.setCulture(culture);
        response.setSaine(iaResult.getMaladie().isSaine());
        response.setConfiance(confiance);

        if (maladie != null) {
            response.setMaladieId(maladie.getId());
            response.setSymptomes(maladie.getSymptomes());
            response.setTraitement(maladie.getTraitement());
        }
        if (iaResult.getTop3() != null) {
            List<DiagnosticAnalyseResponseDTO.Top3Item> top3 =
                    iaResult.getTop3().stream()
                            .map(t -> new DiagnosticAnalyseResponseDTO.Top3Item(
                                    t.getCode(),
                                    t.getNomFr(),
                                    t.getConfiance()
                            ))
                            .collect(Collectors.toList());
            response.setTop3(top3);
        }

        return response;
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