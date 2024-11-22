package com.seuprojeto.projeto_web.controllers;


import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.requests.VehicleEditRequest;
import com.seuprojeto.projeto_web.requests.VehicleRequest;
import com.seuprojeto.projeto_web.services.VehicleService;

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

    @PostMapping
    public ResponseEntity<VehicleEntity> postVehicle(@Valid @RequestBody VehicleRequest veiculo) throws EntityNotFoundException, DuplicateRegisterException {
        return ResponseEntity.ok(veiculoService.registerVehicle(veiculo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleEntity> editVehicle(@PathVariable Long id,@Valid @RequestBody VehicleEditRequest veiculoAtualizado) {
        return ResponseEntity.ok(veiculoService.editVehicle(id, veiculoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        veiculoService.deleteVehicleById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VehicleEntity>> listarVeiculos() {
        return ResponseEntity.ok(veiculoService.listVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleEntity> getById(@PathVariable Long id) throws EntityNotFoundException {
        VehicleEntity veiculo = veiculoService.getVehicleById(id);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<VehicleEntity>> veiculosDisponiveis(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return ResponseEntity.ok(veiculoService.vehiclesAvailableForRent(inicio, fim));
    }

}
