package com.seuprojeto.projeto_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.entities.RentalEntity;
import com.seuprojeto.projeto_web.repositories.ClientRepository;
import com.seuprojeto.projeto_web.repositories.RentalRepository;

@RestController
@RequestMapping("api/rental")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private ClientRepository clientRepository;
    
    @PostMapping
    public ResponseEntity<RentalEntity> createRental(@RequestBody RentalEntity rentalEntity) {

        // Verifique se o cliente existe antes de prosseguir
        if (!clientRepository.existsById(rentalEntity.getClientId())) {
            return ResponseEntity.badRequest().body(null); // ou uma mensagem de erro apropriada
        }

        rentalRepository.save(rentalEntity);
        return ResponseEntity.ok(rentalEntity);
    }

}
