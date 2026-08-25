package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.ConversationIACreateDTO;
import com.projet.sunuagri.dto.ConversationIADTO;
import com.projet.sunuagri.entity.ConversationIA;
import com.projet.sunuagri.repository.ConversationIARepository;
import com.projet.sunuagri.repository.UtilisateurRepository;
import com.projet.sunuagri.service.ConversationIAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationIAServiceImpl
        implements ConversationIAService {

    private final ConversationIARepository conversationRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public ConversationIADTO creer(
            ConversationIACreateDTO dto) {

        // Vérifier que l'utilisateur existe
        utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur introuvable"
                        ));

        ConversationIA conversation = new ConversationIA();

        conversation.setDateCreation(
                LocalDateTime.now()
        );

        // On stocke directement l'id de l'utilisateur
        conversation.setUtilisateurId(
                dto.getUtilisateurId()
        );

        return convertirEnDTO(
                conversationRepository.save(conversation)
        );
    }

    @Override
    public ConversationIADTO trouverParId(Long id) {

        ConversationIA conversation =
                conversationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Conversation introuvable"
                                ));

        return convertirEnDTO(conversation);
    }

    @Override
    public List<ConversationIADTO> trouverToutes() {

        return conversationRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<ConversationIADTO> trouverParUtilisateur(
            Long utilisateurId) {

        return conversationRepository
                .findAll()
                .stream()
                .filter(c ->
                        c.getUtilisateurId()
                                .equals(utilisateurId)
                )
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public void supprimer(Long id) {

        if (!conversationRepository.existsById(id)) {
            throw new RuntimeException(
                    "Conversation introuvable"
            );
        }

        conversationRepository.deleteById(id);
    }

    private ConversationIADTO convertirEnDTO(
            ConversationIA conversation) {

        return new ConversationIADTO(
                conversation.getId(),
                conversation.getDateCreation(),
                conversation.getUtilisateurId()
        );
    }
}