package com.seuprojeto.projeto_web.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

import com.seuprojeto.projeto_web.enums.Exchange;

@Data
public class VehicleRequest {

    @NotBlank(message = "O nome do veículo é obrigatório.")
    private String name;

    @NotBlank(message = "O fabricante é obrigatório.")
    private String manufacturer;

    @NotBlank(message = "A versão é obrigatória.")
    private String version;

    @NotBlank(message = "A URL FIPE é obrigatória.")
    private String urlFipe;

    @NotBlank(message = "A placa é obrigatória.")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres.")
    private String plate;

    @NotBlank(message = "A cor é obrigatória.")
    private String color;

    @NotNull(message = "O tipo de câmbio é obrigatório.")
    private Exchange exchange;

    @NotNull(message = "A quilometragem é obrigatória.")
    @PositiveOrZero(message = "A quilometragem deve ser um valor positivo ou zero.")
    private Double km;

    @NotNull(message = "A capacidade de passageiros é obrigatória.")
    @Min(value = 1, message = "A capacidade de passageiros deve ser no mínimo 1.")
    private Integer capacityPassengers;

    @NotNull(message = "O volume de carga é obrigatório.")
    @PositiveOrZero(message = "O volume de carga deve ser um valor positivo ou zero.")
    private Integer volumeLoad;

    @NotNull(message = "A disponibilidade do veículo é obrigatória.")
    private Boolean available = true;

    @NotEmpty(message = "Os acessórios são obrigatórios.")
    private List<String> accessories;

    @NotNull(message = "O valor da diária é obrigatório.")
    @Positive(message = "O valor da diária deve ser um valor positivo.")
    private Double valuedaily;

    @NotNull(message = "A categoria do veículo é obrigatória.")
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

    public <T> VehicleRequest(String carro, String fabricanteX, String s, String url, String s1, String preto, Exchange exchange, double v, int i, int i1, boolean b, List<T> list, double v1) {
    }
}
