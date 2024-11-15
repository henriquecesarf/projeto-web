package com.seuprojeto.projeto_web.requests;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {

    @NotNull(message = "O id do cliente do veículo é obrigatória.")
    private Long clientId;

    @NotNull(message = "A id do veículo é obrigatório.")
    private Long vehicleId;

    private List<OptionalRequest> optionals;

    @NotNull(message = "A data e hora da locação do veículo é obrigatória.")
    private LocalDateTime rentalDateTimeStart;

    @NotNull(message = "O total de diarias do veículo é obrigatória.")
    private Integer totalDays;

    @NotNull(message = "O valor da caução do veículo é obrigatória.")
    private Double depositAmount;

    @NotNull(message = "A km inicial do veículo é obrigatória.")
    private Double initialMileage;

    @NotNull(message = "O estados da locação do vehicle é obrigatória.")
    private boolean isActive = true;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionalRequest {
        private Long optionalId;
        private int quantity;
    }

}
