package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.DiagnosticCreateDTO;
import com.projet.sunuagri.dto.DiagnosticDTO;
import com.projet.sunuagri.service.DiagnosticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnostics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    @PostMapping
    public ResponseEntity<DiagnosticDTO> creer(
            @Valid @RequestBody DiagnosticCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(diagnosticService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<DiagnosticDTO>> trouverTous() {

        return ResponseEntity.ok(
                diagnosticService.trouverTous()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                diagnosticService.trouverParId(id)
        );
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<DiagnosticDTO>> trouverParUtilisateur(
            @PathVariable Long utilisateurId) {

        return ResponseEntity.ok(
                diagnosticService.trouverParUtilisateur(
                        utilisateurId
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody DiagnosticCreateDTO dto) {

        return ResponseEntity.ok(
                diagnosticService.modifier(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        diagnosticService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}