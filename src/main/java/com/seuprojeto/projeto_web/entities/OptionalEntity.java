package com.seuprojeto.projeto_web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Column;

@Entity
@Data
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

}

