package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.ParcelleCreateDTO;
import com.projet.sunuagri.dto.ParcelleDTO;
import com.projet.sunuagri.service.ParcelleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcelles")
@CrossOrigin(origins = "*")
public class ParcelleController {

    private final ParcelleService parcelleService;

    public ParcelleController(ParcelleService parcelleService) {
        this.parcelleService = parcelleService;
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<ParcelleDTO>> getParUtilisateur(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(parcelleService.getParcellesParUtilisateur(utilisateurId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelleDTO> getParId(@PathVariable Long id) {
        return ResponseEntity.ok(parcelleService.getParcelleParId(id));
    }

    @PostMapping
    public ResponseEntity<ParcelleDTO> creer(@Valid @RequestBody ParcelleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parcelleService.creerParcelle(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelleDTO> modifier(@PathVariable Long id, @Valid @RequestBody ParcelleCreateDTO dto) {
        return ResponseEntity.ok(parcelleService.modifierParcelle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        parcelleService.supprimerParcelle(id);
        return ResponseEntity.noContent().build();
    }
}