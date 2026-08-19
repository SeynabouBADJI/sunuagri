package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.PlanteCreateDTO;
import com.projet.sunuagri.dto.PlanteDTO;
import com.projet.sunuagri.service.PlanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class PlanteController {

    private final PlanteService planteService;

    @PostMapping
    public ResponseEntity<PlanteDTO> creer(
            @Valid @RequestBody PlanteCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planteService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<PlanteDTO>> trouverToutes() {

        return ResponseEntity.ok(
                planteService.trouverToutes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanteDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                planteService.trouverParId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanteDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody PlanteCreateDTO dto) {

        return ResponseEntity.ok(
                planteService.modifier(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        planteService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}