package com.seuprojeto.projeto_web.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

import com.seuprojeto.projeto_web.enums.Exchange;

@Data
public class VehicleRequest {

    @NotBlank(message = "The vehicle name is required.")
    private String name;

    @NotBlank(message = "The manufacturer is required.")
    private String manufacturer;

    @NotBlank(message = "The version is required.")
    private String version;

    @NotBlank(message = "The FIPE URL is required.")
    private String urlFipe;

    @NotBlank(message = "The license plate is required.")
    @Size(min = 7, max = 7, message = "The license plate must have exactly 7 characters.")
    private String plate;

    @NotBlank(message = "The color is required.")
    private String color;

    @NotNull(message = "The type of transmission is required.")
    private Exchange exchange;

    @NotNull(message = "The mileage is required.")
    @PositiveOrZero(message = "The mileage must be a positive value or zero.")
    private Double km;

    @NotNull(message = "The passenger capacity is required.")
    @Min(value = 1, message = "The passenger capacity must be at least 1.")
    private Integer capacityPassengers;

    @NotNull(message = "The cargo volume is required.")
    @PositiveOrZero(message = "The cargo volume must be a positive value or zero.")
    private Integer volumeLoad;

    @NotNull(message = "The vehicle availability is required.")
    private Boolean available = true;

    @NotEmpty(message = "Accessories are required.")
    private List<String> accessories;

    @NotNull(message = "The daily rate is required.")
    @Positive(message = "The daily rate must be a positive value.")
    private Double valuedaily;

    @NotNull(message = "The vehicle category is required.")
    private Long categoryId;


    public VehicleRequest() {
    }

    public VehicleRequest(String name, String manufacturer, String version, String urlFipe, String plate, String color, Exchange exchange, Double km, Integer capacityPassengers, Integer volumeLoad, Boolean available, List<String> accessories, Double valuedaily, Long categoryId) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.version = version;
        this.urlFipe = urlFipe;
        this.plate = plate;
        this.color = color;
        this.exchange = exchange;
        this.km = km;
        this.capacityPassengers = capacityPassengers;
        this.volumeLoad = volumeLoad;
        this.available = available;
        this.accessories = accessories;
        this.valuedaily = valuedaily;
        this.categoryId = categoryId;
    }

}
