package com.projet.sunuagri.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AssistantService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String OLLAMA_URL =
            "http://localhost:11434/api/generate";

    public String genererReponse(String message) {

        Map<String, Object> request = Map.of(
                "model", "llama3",
                "prompt", """
                        Tu es l'assistant agricole intelligent de SunuAgri.

                        Tu aides principalement les agriculteurs sénégalais.

                        Donne des réponses :
                        - simples et faciles à comprendre ;
                        - pratiques ;
                        - adaptées à l'agriculture ;
                        - en français ;
                        - avec des conseils concrets.

                        Si la question ne concerne pas l'agriculture,
                        réponds poliment que tu es spécialisé dans
                        l'accompagnement agricole.

                        Question de l'utilisateur :
                        """ + message,

                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        OLLAMA_URL,
                        entity,
                        Map.class
                );

        if (response.getBody() == null) {
            throw new RuntimeException(
                    "Réponse vide de Ollama"
            );
        }

        Object reponse =
                response.getBody().get("response");

        if (reponse == null) {
            throw new RuntimeException(
                    "Ollama n'a pas retourné de réponse"
            );
        }

        return reponse.toString();
    }
}