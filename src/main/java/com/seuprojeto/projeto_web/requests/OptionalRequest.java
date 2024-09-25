package com.seuprojeto.projeto_web.requests;

public class OptionalRequest {

    private String name;

    private Double valueLocation;

    private Double valueDeclared;

    private Integer qtdAvailable;

    public OptionalRequest() {}

    public OptionalRequest(String name, Double valueLocation, Double valueDeclared, Integer qtdAvailable) {
        this.name = name;
        this.valueLocation = valueLocation;
        this.valueDeclared = valueDeclared;
        this.qtdAvailable = qtdAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValueLocation() {
        return valueLocation;
    }

    public void setValueLocation(Double valueLocation) {
        this.valueLocation = valueLocation;
    }

    public Double getValueDeclared() {
        return valueDeclared;
    }

    public void setValueDeclared(Double valueDeclared) {
        this.valueDeclared = valueDeclared;
    }

    public Integer getQtdAvailable() {
        return qtdAvailable;
    }

    public void setQtdAvailable(Integer qtdAvailable) {
        this.qtdAvailable = qtdAvailable;
    }

}
