package com.projet.sunuagri.dto;

public class AssistantResponseDTO {

    private String type;
    private String contenu;

    public AssistantResponseDTO() {
    }

    public AssistantResponseDTO(String type, String contenu) {
        this.type = type;
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}