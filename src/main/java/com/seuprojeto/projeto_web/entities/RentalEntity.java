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

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client; // // Referência ao cliente associado

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false) // Chave estrangeira para o veículo
    private VehicleEntity vehicle; // Referência ao veículo associado

    @Column(nullable = true)
    private String optionals; // String JSON para armazenar IDs e quantidades dos opcionais

    @Column(nullable = false)
    private LocalDateTime rentalDateTimeStart; // Data e hora da locação inicio

    @Column(nullable = false)
    private LocalDateTime rentalDateTimeEnd; // Data e hora da locaçãofim

    @Column(nullable = false)
    private Double dailyRate; // Valor da diária

    @Column(nullable = false)
    private Integer totalDays; // Total de diárias

    @Column(nullable = false)
    private Double totalAmount; // Valor a ser pago

    @Column(nullable = false)
    private Double depositAmount; // Valor da caução

    @Column(nullable = true)
    private Double totalOptionalItemsValue; // Valor total dos itens opcionais

    @Column(nullable = false)
    private String plateVehicle; // Placa do veículo

    @Column(nullable = false)
    private Double initialMileage; // Quilometragem de retirada

    @Column(nullable = false)
    private Double returnMileage; // Quilometragem de devolução

    @Column(nullable = false)
    private LocalDateTime registrationDate; // Data de cadastro

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

}
