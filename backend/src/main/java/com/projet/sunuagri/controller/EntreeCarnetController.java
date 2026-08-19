package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.EntreeCarnetCreateDTO;
import com.projet.sunuagri.dto.EntreeCarnetDTO;
import com.projet.sunuagri.service.EntreeCarnetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrees-carnet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class EntreeCarnetController {

    private final EntreeCarnetService entreeCarnetService;

    @PostMapping
    public ResponseEntity<EntreeCarnetDTO> creer(
            @Valid @RequestBody EntreeCarnetCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entreeCarnetService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<EntreeCarnetDTO>> trouverToutes() {

        return ResponseEntity.ok(
                entreeCarnetService.trouverToutes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntreeCarnetDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                entreeCarnetService.trouverParId(id)
        );
    }

    @GetMapping("/parcelle/{parcelleId}")
    public ResponseEntity<List<EntreeCarnetDTO>> trouverParParcelle(
            @PathVariable Long parcelleId) {

        return ResponseEntity.ok(
                entreeCarnetService.trouverParParcelle(parcelleId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntreeCarnetDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody EntreeCarnetCreateDTO dto) {

        return ResponseEntity.ok(
                entreeCarnetService.modifier(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        entreeCarnetService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}