package com.projet.sunuagri.dto;

import com.projet.sunuagri.entity.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long id;

    private String contenu;

    private LocalDateTime dateEnvoi;

    private TypeMessage type;

    private Long conversationId;
}