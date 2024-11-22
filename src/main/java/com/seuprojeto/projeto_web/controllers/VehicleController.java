package com.seuprojeto.projeto_web.controllers;


import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.requests.VehicleRequest;
import com.seuprojeto.projeto_web.services.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService veiculoService;

    @Operation(
            summary = "Criação veículo",
            description = "End-point de criação de veículos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Retorna um Json com o objeto do veículo criado"),
    })
    @PostMapping
    public ResponseEntity<VehicleEntity> postVehicle(@Valid @RequestBody VehicleRequest veiculo) throws EntityNotFoundException, DuplicateRegisterException {
        return ResponseEntity.ok(veiculoService.registerVehicle(veiculo));
    }

    @Operation(
            summary = "Alteração de veículos",
            description = "Endpoint para fazer a alteração de uma veículos pelo ID.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com os dados da veículo Alterado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleEntity> editVehicle(@PathVariable Long id, @RequestBody VehicleEntity veiculoAtualizado) {
        return ResponseEntity.ok(veiculoService.editVehicle(id, veiculoAtualizado));
    }

    @Operation(
            summary = "Delete de veículos",
            description = "Endpoint para fazer Exclusão de um Veículo.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ""),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        veiculoService.deleteVehicleById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get todos os veículos",
            description = "Endpoint para consultar todas os veículos cadastrados.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todos os veículos"),
    })
    @GetMapping
    public ResponseEntity<List<VehicleEntity>> listarVeiculos() {
        return ResponseEntity.ok(veiculoService.listVehicles());
    }

    @Operation(
            summary = "Get todos os veículos disponíveis",
            description = "Endpoint para consultar todas as veículos que ainda não estão locados.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todos os veículos"),
    })
    @GetMapping("/disponiveis")
    public ResponseEntity<List<VehicleEntity>> veiculosDisponiveis(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return ResponseEntity.ok(veiculoService.vehiclesAvailableForRent(inicio, fim));
    }

}
