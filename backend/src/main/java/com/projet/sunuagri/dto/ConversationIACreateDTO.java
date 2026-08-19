package com.projet.sunuagri.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationIACreateDTO {

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long utilisateurId;
}