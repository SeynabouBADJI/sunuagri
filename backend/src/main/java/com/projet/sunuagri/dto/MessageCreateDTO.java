package com.projet.sunuagri.dto;

import com.projet.sunuagri.entity.TypeMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateDTO {

    @NotBlank(message = "Le contenu du message est obligatoire")
    private String contenu;

    @NotNull(message = "Le type du message est obligatoire")
    private TypeMessage type;

    @NotNull(message = "La conversation est obligatoire")
    private Long conversationId;
}