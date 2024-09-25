package com.seuprojeto.projeto_web.enums;

public enum Gravity {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private String value;

    Gravity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

