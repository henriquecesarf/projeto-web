package com.seuprojeto.projeto_web.enums;

public enum Exchange {

    MANUAL("MANUAL"),
    AUTOMATIC("AUTOMATIC");

    private String value;

    Exchange(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
