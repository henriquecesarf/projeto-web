package com.seuprojeto.projeto_web.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String manufacturer; //fabricante

    @Column(nullable = false)
    private String version; //versão

    @Column(nullable = false)
    private String urlFipe;

    @Column(unique = true)
    private String plate; //placa

    @Column(nullable = false)
    private String color; //color

    @Column(nullable = false)
    private String exchange; //cambio

    @Column(nullable = false)
    private Double km; //kilometragem

    @Column(nullable = false)
    private Integer capacityPassengers;//capacidade de passageiros

    @Column(nullable = false)
    private Integer volumeLoad; //volume de carga

    @Column(nullable = false)
    private Boolean available = true; //disponivel

    @Column(nullable = false)
    @ElementCollection
    private List<String> accessories; //acessorios

    @Column(nullable = false)
    private Double valuedaily; //valor diaria

    @Column(nullable = false)
    private LocalDateTime registrationDate; // Data de registro

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category; //categoria

    @OneToMany(mappedBy = "vehicle")
    private List<RentalEntity> rentals; //alugueis
}
