package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.AlerteCreateDTO;
import com.projet.sunuagri.dto.AlerteDTO;
import com.projet.sunuagri.service.AlerteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class AlerteController {

    private final AlerteService alerteService;

    @PostMapping
    public ResponseEntity<AlerteDTO> creer(
            @Valid @RequestBody AlerteCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alerteService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<AlerteDTO>> trouverToutes() {

        return ResponseEntity.ok(
                alerteService.trouverToutes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlerteDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alerteService.trouverParId(id)
        );
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<AlerteDTO>> trouverParRegion(
            @PathVariable String region) {

        return ResponseEntity.ok(
                alerteService.trouverParRegion(region)
        );
    }

    @GetMapping("/maladie/{maladieId}")
    public ResponseEntity<List<AlerteDTO>> trouverParMaladie(
            @PathVariable Long maladieId) {

        return ResponseEntity.ok(
                alerteService.trouverParMaladie(maladieId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlerteDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody AlerteCreateDTO dto) {

        return ResponseEntity.ok(
                alerteService.modifier(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        alerteService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}