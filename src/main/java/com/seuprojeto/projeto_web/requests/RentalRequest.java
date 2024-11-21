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

    @NotNull(message = "The client ID of the vehicle is required.")
    private Long clientId;

    @NotNull(message = "The vehicle ID is required.")
    private Long vehicleId;

    private List<OptionalRequest> optionals;

    @NotNull(message = "The start date and time of the vehicle rental is required.")
    private LocalDateTime rentalDateTimeStart;

    @NotNull(message = "The total number of rental days is required.")
    private Integer totalDays;

    @NotNull(message = "The deposit amount for the vehicle is required.")
    private Double depositAmount;

    @NotNull(message = "The initial mileage of the vehicle is required.")
    private Double initialMileage;

    @NotNull(message = "The rental status of the vehicle is required.")
    private boolean isActive = true;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionalRequest {
        private Long optionalId;
        private int quantity;
    }

}
