package com.seuprojeto.projeto_web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private ClientEntity client; // Chave estrangeira para o cliente associado

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private VehicleEntity vehicle; // Chave estrangeira para o veículo associado

    @Column(nullable = true)
    private String optionals; // String JSON para armazenar IDs e quantidades dos opcionais

    @Column(nullable = false)
    private LocalDateTime rentalDateTimeStart; // Data e hora da locação inicio

    // @Column(nullable = false)
    private LocalDateTime rentalDateTimeEnd; // Data e hora da locaçãofim

    @Column(nullable = false)
    private Double dailyRate; // Valor da diária

    @Column(nullable = false)
    private Integer totalDays; // Total de diárias

    // @Column(nullable = false)
    private Double totalAmount; // Valor a ser pago

    @Column(nullable = false)
    private Double depositAmount; // Valor da caução

    // @Column(nullable = true)
    private Double totalOptionalItemsValue; // Valor total dos itens opcionais

    // @Column(nullable = false)
    private String plateVehicle; // Placa do veículo

    @Column(nullable = false)
    private Double initialMileage; // Quilometragem de retirada

    // @Column(nullable = false)
    private Double returnMileage; // Quilometragem de devolução

    @Column(nullable = false)
    private LocalDateTime registrationDate; // Data de cadastro

    private Double amountPaid;

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "rental", cascade = CascadeType.REMOVE)
    private List<RentalSinister> rentalSinisters = new ArrayList<>();

    @Override
    public String toString() {
        return "RentalEntity [id=" + id + ", client=" + client + ", vehicle=" + vehicle + ", optionals=" + optionals
                + ", rentalDateTimeStart=" + rentalDateTimeStart + ", rentalDateTimeEnd=" + rentalDateTimeEnd
                + ", dailyRate=" + dailyRate + ", totalDays=" + totalDays + ", totalAmount=" + totalAmount
                + ", depositAmount=" + depositAmount + ", totalOptionalItemsValue=" + totalOptionalItemsValue
                + ", plateVehicle=" + plateVehicle + ", initialMileage=" + initialMileage + ", returnMileage="
                + returnMileage + ", registrationDate=" + registrationDate + ", amountPaid=" + amountPaid
                + ", isActive=" + isActive + "]";
    }    

}
