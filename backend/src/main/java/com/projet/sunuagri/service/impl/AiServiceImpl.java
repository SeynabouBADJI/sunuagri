package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.AiPredictionResponseDTO;
import com.projet.sunuagri.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class AiServiceImpl implements AiService {

    @Value("${sunuagri.ai.url:http://localhost:8000}")
    private String aiServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public AiPredictionResponseDTO analyserImage(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image vide ou nulle");
        }

        String url = aiServiceUrl + "/predict";
        log.info("🔍 Envoi image au service IA : {}", url);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            ByteArrayResource fileResource = new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename() != null
                            ? image.getOriginalFilename()
                            : "image.jpg";
                }
            };

            body.add("file", fileResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<AiPredictionResponseDTO> response =
                    restTemplate.postForEntity(url, requestEntity, AiPredictionResponseDTO.class);

            AiPredictionResponseDTO result = response.getBody();

            if (result != null && result.getMaladie() != null) {
                log.info("✅ Prédiction : {} ({}%)",
                        result.getMaladie().getCode(),
                        result.getConfiance());
            }

            return result;

        } catch (IOException e) {
            log.error("❌ Erreur lecture image", e);
            throw new RuntimeException("Impossible de lire l'image : " + e.getMessage(), e);

        } catch (Exception e) {
            log.error("❌ Erreur appel service IA", e);
            throw new RuntimeException(
                    "Le service IA est indisponible sur " + aiServiceUrl, e);
        }
    }

    @Override
    public boolean verifierDisponibilite() {
        try {
            String url = aiServiceUrl + "/health";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.warn("⚠️ Service IA indisponible : {}", e.getMessage());
            return false;
        }
    }
}