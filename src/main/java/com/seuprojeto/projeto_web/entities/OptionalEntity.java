package com.seuprojeto.projeto_web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "optional")
public class OptionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "value_location", nullable = false)
    private Double valueLocation;

    @Column(name = "value_declared", nullable = false)
    private Double valueDeclared;

    @Column(name = "qtd_available", nullable = false)
    private Integer qtdAvailable;

    public OptionalEntity() {}

    public OptionalEntity(String name, Double valueLocation, Double valueDeclared, Integer qtdAvailable) {
        this.name = name;
        this.valueLocation = valueLocation;
        this.valueDeclared = valueDeclared;
        this.qtdAvailable = qtdAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

