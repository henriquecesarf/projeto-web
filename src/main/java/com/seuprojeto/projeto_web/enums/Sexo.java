package com.seuprojeto.projeto_web.enums;

public enum Sexo {
    M("M"),
    F("F"),
    X("X");

    private String value;

    Sexo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
