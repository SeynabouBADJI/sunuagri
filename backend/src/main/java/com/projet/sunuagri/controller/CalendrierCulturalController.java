package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.CalendrierCulturalCreateDTO;
import com.projet.sunuagri.dto.CalendrierCulturalDTO;
import com.projet.sunuagri.service.CalendrierCulturalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendriers-culturaux")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class CalendrierCulturalController {

    private final CalendrierCulturalService calendrierService;

    @PostMapping
    public ResponseEntity<CalendrierCulturalDTO> creer(
            @Valid @RequestBody
            CalendrierCulturalCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(calendrierService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<CalendrierCulturalDTO>>
    trouverTous() {

        return ResponseEntity.ok(
                calendrierService.trouverTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendrierCulturalDTO>
    trouverParId(@PathVariable Long id) {

        return ResponseEntity.ok(
                calendrierService.trouverParId(id));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<CalendrierCulturalDTO>>
    trouverParRegion(@PathVariable String region) {

        return ResponseEntity.ok(
                calendrierService.trouverParRegion(region));
    }

    @GetMapping("/plante/{planteId}")
    public ResponseEntity<List<CalendrierCulturalDTO>>
    trouverParPlante(@PathVariable Long planteId) {

        return ResponseEntity.ok(
                calendrierService.trouverParPlante(planteId));
    }

    @GetMapping("/saison/{saison}")
    public ResponseEntity<List<CalendrierCulturalDTO>>
    trouverParSaison(@PathVariable String saison) {

        return ResponseEntity.ok(
                calendrierService.trouverParSaison(saison));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendrierCulturalDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody
            CalendrierCulturalCreateDTO dto) {

        return ResponseEntity.ok(
                calendrierService.modifier(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        calendrierService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}