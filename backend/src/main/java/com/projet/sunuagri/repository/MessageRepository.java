package com.projet.sunuagri.repository;

import com.projet.sunuagri.entity.Message;
import com.projet.sunuagri.entity.TypeMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository
        extends JpaRepository<Message, Long> {

    List<Message> findByConversationIdOrderByDateEnvoiAsc(
            Long conversationId
    );

    List<Message> findByType(TypeMessage type);
}