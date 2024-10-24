package com.seuprojeto.projeto_web.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rental")
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clientId; // Entidade Cliente

    @Column(nullable = false)
    private String optionals;

    @Column(nullable = false)
    private LocalDateTime rentalDateTimeStart; // Data e hora da locação inicio

    @Column(nullable = false)
    private LocalDateTime rentalDateTimeEnd; // Data e hora da locaçãofim

    @Column(nullable = false)
    private double dailyRate; // Valor da diária

    @Column(nullable = false)
    private int totalDays; // Total de diárias

    @Column(nullable = false)
    private double totalAmount; // Valor a ser pago

    @Column(nullable = false)
    private double depositAmount; // Valor da caução

    @Column(nullable = false)
    private double totalOptionalItemsValue; // Valor total dos itens opcionais

    @Column(nullable = false)
    private String vehiclePlate; // Placa do veículo

    @Column(nullable = false)
    private double initialMileage; // Quilometragem de retirada

    @Column(nullable = false)
    private double returnMileage; // Quilometragem de devolução

    @Column(nullable = false)
    private LocalDateTime registrationDate; // Data de cadastro

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "vehicleId", nullable = false)
    private VehicleEntity vehicle;

}
