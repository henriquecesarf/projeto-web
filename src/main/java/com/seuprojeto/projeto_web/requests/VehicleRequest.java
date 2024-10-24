package com.seuprojeto.projeto_web.requests;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehicleRequest {

    @NotBlank(message = "O nome do veículo é obrigatório.")
    private String nome;

    @NotBlank(message = "O fabricante é obrigatório.")
    private String fabricante;

    @NotBlank(message = "A versão é obrigatória.")
    private String versao;

    @NotBlank(message = "A URL FIPE é obrigatória.")
    private String urlFipe;

    @NotBlank(message = "A placa é obrigatória.")
    private String placa;

    @NotBlank(message = "A cor é obrigatória.")
    private String cor;

    @NotBlank(message = "O tipo de câmbio é obrigatório.")
    private String cambio;

    @NotNull(message = "A quilometragem é obrigatória.")
    @PositiveOrZero(message = "A quilometragem deve ser um valor positivo ou zero.")
    private Double quilometragem;

    @NotNull(message = "A capacidade de passageiros é obrigatória.")
    @Min(value = 1, message = "A capacidade de passageiros deve ser no mínimo 1.")
    private Integer capacidadePassageiros;

    @NotNull(message = "O volume de carga é obrigatório.")
    @PositiveOrZero(message = "O volume de carga deve ser um valor positivo ou zero.")
    private Integer volumeCarga;

    @NotNull(message = "A disponibilidade do veículo é obrigatória.")
    private Boolean disponivel;

    @NotEmpty(message = "Os acessórios são obrigatórios.")
    private List<String> acessorios;

    @NotNull(message = "O valor da diária é obrigatório.")
    @Positive(message = "O valor da diária deve ser um valor positivo.")
    private Double valorDiaria;

    @NotNull(message = "A categoria do veículo é obrigatória.")
    private Long categoriaId;

    public VehicleRequest() {
    }

    public VehicleRequest(String nome, String fabricante, String versao, String urlFipe, String placa, String cor, String cambio, Double quilometragem, Integer capacidadePassageiros, Integer volumeCarga, Boolean disponivel, List<String> acessorios, Double valorDiaria, Long categoriaId) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.versao = versao;
        this.urlFipe = urlFipe;
        this.placa = placa;
        this.cor = cor;
        this.cambio = cambio;
        this.quilometragem = quilometragem;
        this.capacidadePassageiros = capacidadePassageiros;
        this.volumeCarga = volumeCarga;
        this.disponivel = disponivel;
        this.acessorios = acessorios;
        this.valorDiaria = valorDiaria;
        this.categoriaId = categoriaId;
    }
}
