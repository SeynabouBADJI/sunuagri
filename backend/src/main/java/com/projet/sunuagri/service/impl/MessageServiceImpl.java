package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.MessageCreateDTO;
import com.projet.sunuagri.dto.MessageDTO;
import com.projet.sunuagri.entity.Message;
import com.projet.sunuagri.repository.ConversationIARepository;
import com.projet.sunuagri.repository.MessageRepository;
import com.projet.sunuagri.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationIARepository conversationRepository;

    @Override
    public MessageDTO creer(MessageCreateDTO dto) {

        // Vérifier que la conversation existe
        conversationRepository.findById(dto.getConversationId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Conversation introuvable"
                        ));

        Message message = new Message();

        message.setContenu(dto.getContenu());
        message.setDateEnvoi(LocalDateTime.now());
        message.setType(dto.getType());

        // On stocke directement l'id de la conversation
        message.setConversationId(
                dto.getConversationId()
        );

        return convertirEnDTO(
                messageRepository.save(message)
        );
    }

    @Override
    public MessageDTO trouverParId(Long id) {

        Message message =
                messageRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Message introuvable"
                                ));

        return convertirEnDTO(message);
    }

    @Override
    public List<MessageDTO> trouverTous() {

        return messageRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public List<MessageDTO> trouverParConversation(
            Long conversationId) {

        return messageRepository
                .findByConversationIdOrderByDateEnvoiAsc(
                        conversationId
                )
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public void supprimer(Long id) {

        if (!messageRepository.existsById(id)) {
            throw new RuntimeException(
                    "Message introuvable"
            );
        }

        messageRepository.deleteById(id);
    }

    private MessageDTO convertirEnDTO(Message message) {

        return new MessageDTO(
                message.getId(),
                message.getContenu(),
                message.getDateEnvoi(),
                message.getType(),
                message.getConversationId()
        );
    }
}