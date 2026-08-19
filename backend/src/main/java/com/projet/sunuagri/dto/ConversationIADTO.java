package com.projet.sunuagri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationIADTO {

    private Long id;

    private LocalDateTime dateCreation;

    private Long utilisateurId;
}