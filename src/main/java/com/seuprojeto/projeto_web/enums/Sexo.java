package com.seuprojeto.projeto_web.enums;

public enum Sexo {
    MASCULINO("MASCULINO"),
    FEMININO("FEMININO");

    private String value;

    Sexo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
