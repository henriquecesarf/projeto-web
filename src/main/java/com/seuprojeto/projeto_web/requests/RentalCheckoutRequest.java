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

    @NotNull(message = "The date and time of vehicle return is mandatory.")
    private LocalDateTime rentalDateTimeEnd;

    @NotNull(message = "Vehicle return mileage is mandatory.")
    private Double returnMileage;

    @NotNull(message = "The amount of payment received is mandatory.")
    private Double amountPaid;

    @PositiveOrZero(message = "The amount corresponding to the collection of claims must be greater than or equal to zero.")
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
