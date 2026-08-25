package com.projet.sunuagri.controller;

import com.projet.sunuagri.service.AssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
@CrossOrigin(origins = "*")
public class AssistantController {

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/message")
    public ResponseEntity<AssistantResponse> envoyerMessage(
            @RequestBody AssistantRequest request) {

        String reponse = assistantService.genererReponse(request.message());

        return ResponseEntity.ok(
                new AssistantResponse(
                        reponse
                )
        );
    }

    public record AssistantRequest(String message) {}

    public record AssistantResponse(String contenu) {}
}