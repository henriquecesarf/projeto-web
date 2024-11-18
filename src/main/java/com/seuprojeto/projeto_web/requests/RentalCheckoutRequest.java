package com.seuprojeto.projeto_web.requests;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalCheckoutRequest {

    @NotNull(message = "A data e hora da devolução do veículo é obrigatória.")
    private LocalDateTime rentalDateTimeEnd;

    @NotNull(message = "A kilometragem de devolução do veículo é obrigatoria.")
    private Double returnMileage;

    @NotNull(message = "O valor do pagamento recebido é obrigatorio.")
    private Double amountPaid;

    @PositiveOrZero(message = "O valor referente a cobrança dos sinistros deve ser maior ou igual a zero.")
    private Double valuesSinisters;

    private List<LostOptionalRequest> lostOptionals;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LostOptionalRequest {
        private Long optionalId;
        private int quantity;
    }

    
}
