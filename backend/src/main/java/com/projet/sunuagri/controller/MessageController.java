package com.projet.sunuagri.controller;

import com.projet.sunuagri.dto.MessageCreateDTO;
import com.projet.sunuagri.dto.MessageDTO;
import com.projet.sunuagri.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8100")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> creer(
            @Valid @RequestBody MessageCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(messageService.creer(dto));
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> trouverTous() {

        return ResponseEntity.ok(
                messageService.trouverTous()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> trouverParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                messageService.trouverParId(id)
        );
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageDTO>>
    trouverParConversation(
            @PathVariable Long conversationId) {

        return ResponseEntity.ok(
                messageService.trouverParConversation(
                        conversationId
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        messageService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}