package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.MaladieCreateDTO;
import com.projet.sunuagri.dto.MaladieDTO;
import com.projet.sunuagri.service.MaladieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maladies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class MaladieController {

    private final MaladieService maladieService;

    @PostMapping
    public ResponseEntity<MaladieDTO> creer(
            @Valid @RequestBody MaladieCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maladieService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<MaladieDTO>> trouverToutes() {

        return ResponseEntity.ok(
                maladieService.trouverToutes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaladieDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maladieService.trouverParId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaladieDTO> modifier(
            @PathVariable Long id,
            @Valid @RequestBody MaladieCreateDTO dto) {

        return ResponseEntity.ok(
                maladieService.modifier(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        maladieService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}