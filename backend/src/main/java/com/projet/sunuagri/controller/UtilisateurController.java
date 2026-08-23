package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.UtilisateurCreateDTO;
import com.projet.sunuagri.dto.UtilisateurDTO;
import com.projet.sunuagri.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getTous() {
        return ResponseEntity.ok(utilisateurService.getTousLesUtilisateurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurParId(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurParId(id));
    }

    @PostMapping
    public ResponseEntity<UtilisateurDTO> creer(@Valid @RequestBody UtilisateurCreateDTO dto) {
        UtilisateurDTO cree = utilisateurService.creerUtilisateur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> modifier(@PathVariable Long id, @Valid @RequestBody UtilisateurCreateDTO dto) {
        return ResponseEntity.ok(utilisateurService.modifierUtilisateur(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}