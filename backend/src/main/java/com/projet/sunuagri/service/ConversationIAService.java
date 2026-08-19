package com.projet.sunuagri.service;

import com.projet.sunuagri.dto.ConversationIACreateDTO;
import com.projet.sunuagri.dto.ConversationIADTO;

import java.util.List;

public interface ConversationIAService {

    ConversationIADTO creer(ConversationIACreateDTO dto);

    ConversationIADTO trouverParId(Long id);

    List<ConversationIADTO> trouverToutes();

    List<ConversationIADTO> trouverParUtilisateur(
            Long utilisateurId);

    void supprimer(Long id);
}