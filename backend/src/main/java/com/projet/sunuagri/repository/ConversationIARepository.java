package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.ConversationIA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationIARepository
        extends JpaRepository<ConversationIA, Long> {

    List<ConversationIA> findByUtilisateurId(Long utilisateurId);

    Optional<ConversationIA> findFirstByUtilisateurIdOrderByDateCreationDesc(
            Long utilisateurId
    );
}