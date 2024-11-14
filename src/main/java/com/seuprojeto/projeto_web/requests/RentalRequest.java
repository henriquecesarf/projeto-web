package com.seuprojeto.projeto_web.requests;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {


    @NotNull(message = "O id do cliente do veículo é obrigatória.")
    private Long clientId;

    @NotNull(message = "A categoria do veículo é obrigatória.")
    private Long vehicleId;

    private List<OptionalRequest> optionals;

    @NotNull(message = "A data e hora da locação do veículo é obrigatória.")
    private LocalDateTime rentalDateTimeStart;

    @NotNull(message = "A data e hora do fim da locação do veículo é obrigatória.")
    private LocalDateTime rentalDateTimeEnd;

    @NotNull(message = "A categoria do veículo é obrigatória.")
    private Double dailyRate;

    @NotNull(message = "O total de diarias do veículo é obrigatória.")
    private Integer totalDays;

    @NotNull(message = "O valor a ser pago pela locação do veículo é obrigatória.")
    private Double totalAmount;

    @NotNull(message = "O valor da caução do veículo é obrigatória.")
    private Double depositAmount;

    private Double totalOptionalItemsValue;

    @NotBlank(message = "A placa do veículo é obrigatório.")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres.")
    private String plateVehicle;

    @NotNull(message = "A km inicial do veículo é obrigatória.")
    private Double initialMileage;

    @NotNull(message = "A km na devolução do veículo é obrigatória.")
    private Double returnMileage;

    @NotNull(message = "A categoria do veículo é obrigatória.")
    private boolean isActive = true;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionalRequest {
        private Long optionalId;
        private int quantity;
    }

}
