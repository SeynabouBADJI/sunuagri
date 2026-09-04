
package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.AuthResponseDTO;
import com.projet.sunuagri.dto.LoginDTO;
import com.projet.sunuagri.dto.UtilisateurCreateDTO;
import com.projet.sunuagri.service.UtilisateurService;
import com.projet.sunuagri.dto.UtilisateurDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginDTO dto) {

        return ResponseEntity.ok(
                utilisateurService.authentifier(dto)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<UtilisateurDTO> register(
            @Valid @RequestBody UtilisateurCreateDTO dto) {

        UtilisateurDTO cree = utilisateurService.creerUtilisateur(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cree);
    }
}