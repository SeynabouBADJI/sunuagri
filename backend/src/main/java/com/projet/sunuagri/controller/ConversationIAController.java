package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.ConversationIACreateDTO;
import com.projet.sunuagri.dto.ConversationIADTO;
import com.projet.sunuagri.service.ConversationIAService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class ConversationIAController {

    private final ConversationIAService conversationService;

    @PostMapping
    public ResponseEntity<ConversationIADTO> creer(
            @Valid @RequestBody
            ConversationIACreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversationService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConversationIADTO>>
    trouverToutes() {

        return ResponseEntity.ok(
                conversationService.trouverToutes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationIADTO>
    trouverParId(@PathVariable Long id) {

        return ResponseEntity.ok(
                conversationService.trouverParId(id)
        );
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<ConversationIADTO>>
    trouverParUtilisateur(
            @PathVariable Long utilisateurId) {

        return ResponseEntity.ok(
                conversationService.trouverParUtilisateur(
                        utilisateurId
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        conversationService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}