package com.seuprojeto.projeto_web.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
    private Long vehicleId; // Entidade Veículo

    @Column(nullable = false)
    private String optionals;

    @Column(nullable = false)
    private LocalDateTime rentalDateTime; // Data e hora da locação

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

}
