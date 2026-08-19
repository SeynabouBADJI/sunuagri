package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.PlantationCreateDTO;
import com.projet.sunuagri.dto.PlantationDTO;
import com.projet.sunuagri.service.PlantationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class PlantationController {

    private final PlantationService plantationService;

    @PostMapping
    public ResponseEntity<PlantationDTO> creer(
            @Valid @RequestBody PlantationCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plantationService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<PlantationDTO>> trouverToutes() {

        return ResponseEntity.ok(
                plantationService.trouverToutes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantationDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                plantationService.trouverParId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantationDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody PlantationCreateDTO dto) {

        return ResponseEntity.ok(
                plantationService.modifier(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        plantationService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}