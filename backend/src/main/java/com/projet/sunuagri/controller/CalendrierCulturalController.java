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
@RequestMapping("/api/calendrier-cultural")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CalendrierCulturalController {

    private final CalendrierCulturalService calendrierService;


    /**
     * Créer un calendrier cultural
     */
    @PostMapping
    public ResponseEntity<CalendrierCulturalDTO> creer(
            @Valid @RequestBody CalendrierCulturalCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(calendrierService.creer(dto));
    }


    /**
     * Récupérer tous les calendriers
     */
    @GetMapping
    public ResponseEntity<List<CalendrierCulturalDTO>> trouverToutes() {

        return ResponseEntity.ok(
                calendrierService.trouverToutes()
        );
    }


    /**
     * Récupérer un calendrier par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalendrierCulturalDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                calendrierService.trouverParId(id)
        );
    }


    /**
     * Récupérer les calendriers d'une plante
     *
     * Exemple :
     * /api/calendrier-cultural/plante/1
     */
    @GetMapping("/plante/{planteId}")
    public ResponseEntity<List<CalendrierCulturalDTO>> trouverParPlante(
            @PathVariable Long planteId) {

        return ResponseEntity.ok(
                calendrierService.trouverParPlante(planteId)
        );
    }


    /**
     * Récupérer les calendriers d'une zone agricole
     *
     * Exemple :
     * /api/calendrier-cultural/zone/Kaolack
     */
    @GetMapping("/zone/{zoneAgricole}")
    public ResponseEntity<List<CalendrierCulturalDTO>> trouverParZone(
            @PathVariable String zoneAgricole) {

        return ResponseEntity.ok(
                calendrierService.trouverParZone(zoneAgricole)
        );
    }


    /**
     * Modifier un calendrier
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalendrierCulturalDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody CalendrierCulturalCreateDTO dto) {

        return ResponseEntity.ok(
                calendrierService.modifier(id, dto)
        );
    }


    /**
     * Supprimer un calendrier
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        calendrierService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}