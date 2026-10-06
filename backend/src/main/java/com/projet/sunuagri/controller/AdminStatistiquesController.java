package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.AdminStatistiquesDTO;
import com.projet.sunuagri.service.AdminStatistiquesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class AdminStatistiquesController {

    private final AdminStatistiquesService adminStatistiquesService;

    @GetMapping("/statistiques")
    public ResponseEntity<AdminStatistiquesDTO> obtenirStatistiques() {

        return ResponseEntity.ok(
                adminStatistiquesService.obtenirStatistiques()
        );
    }
}