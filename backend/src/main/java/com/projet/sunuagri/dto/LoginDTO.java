package com.projet.sunuagri.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "Email invalide")
    @NotBlank
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}