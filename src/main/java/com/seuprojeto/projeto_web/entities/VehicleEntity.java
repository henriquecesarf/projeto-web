package com.seuprojeto.projeto_web.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String fabricante;
    private String versao;
    private String urlFipe;

    @Column(unique = true)
    private String placa;

    private String cor;
    private String cambio;
    private Double quilometragem;
    private Integer capacidadePassageiros;
    private Integer volumeCarga;
    private Boolean disponivel;

    @ElementCollection
    private List<String> acessorios;

    private Double valorDiaria;

    @Column(nullable = false)
    private LocalDateTime registrationDate; // Data de cadastro

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoria;

    @OneToMany(mappedBy = "vehicle")
    private List<RentalEntity> rentals;
}
