package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.MessageCreateDTO;
import com.projet.sunuagri.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    MessageDTO creer(MessageCreateDTO dto);

    MessageDTO trouverParId(Long id);

    List<MessageDTO> trouverTous();

    List<MessageDTO> trouverParConversation(
            Long conversationId
    );

    void supprimer(Long id);
}