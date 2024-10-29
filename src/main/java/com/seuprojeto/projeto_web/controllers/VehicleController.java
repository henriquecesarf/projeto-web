package com.seuprojeto.projeto_web.controllers;


import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.requests.VehicleRequest;
import com.seuprojeto.projeto_web.services.VehicleService;
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
    public ResponseEntity<VehicleRequest> cadastrarVeiculo(@RequestBody VehicleRequest veiculo) {
        return ResponseEntity.ok(veiculoService.registerVehicle(veiculo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleEntity> editarVeiculo(@PathVariable Long id, @RequestBody VehicleEntity veiculoAtualizado) {
        return ResponseEntity.ok(veiculoService.editVehicle(id, veiculoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
        veiculoService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VehicleEntity>> listarVeiculos() {
        return ResponseEntity.ok(veiculoService.listVehicles());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<VehicleEntity>> veiculosDisponiveis(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return ResponseEntity.ok(veiculoService.vehiclesAvailableForRent(inicio, fim));
    }

}
