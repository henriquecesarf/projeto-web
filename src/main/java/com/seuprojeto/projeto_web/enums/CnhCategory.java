package com.seuprojeto.projeto_web.enums;

public enum CnhCategory {

    // Motocicletas, motonetas e triciclos
    A("A"),
    // Carros, picapes e vans
    B("B"),
    // Caminhões, caminhonetes e vans de Carga
    C("C"),
    // Ônibus, micro-ônibus, vans de passageiros
    E("D"),
    // Treminhão, ônibus articulados, veículos com trailers
    D("E");

    private String value;

    CnhCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
