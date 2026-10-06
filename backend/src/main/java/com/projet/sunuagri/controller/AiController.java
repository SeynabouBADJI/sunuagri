package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.AiPredictionResponseDTO;
import com.projet.sunuagri.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    @PostMapping(value = "/predict", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AiPredictionResponseDTO> predict(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(aiService.analyserImage(file));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {

        boolean dispo = aiService.verifierDisponibilite();

        return ResponseEntity.ok(Map.of(
                "ai_service", dispo ? "ok" : "indisponible",
                "available", dispo
        ));
    }
}