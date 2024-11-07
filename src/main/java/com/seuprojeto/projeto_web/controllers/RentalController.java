package com.seuprojeto.projeto_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.RentalRequest;
import com.seuprojeto.projeto_web.services.RentalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<RentalRequest>> getAllRentals() throws TableEmptyException {
        List<RentalRequest> rentals = rentalService.findAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalRequest> getById(@PathVariable Long id) throws EntityNotFoundException {
        RentalRequest rental = rentalService.findRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @PostMapping
    public ResponseEntity<RentalRequest> postRental(@Valid @RequestBody RentalRequest rentalRequest) throws EntityNotFoundException {
        RentalRequest rental = rentalService.createRental(rentalRequest) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRentalbyId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalRequest> putRental(@PathVariable Long id,@Valid  @RequestBody RentalRequest rentalRequest) {
        RentalRequest updatedRental = rentalService.updateRentalById(id, rentalRequest);
        return ResponseEntity.ok(updatedRental);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RentalRequest> patchRental(@PathVariable Long id,@RequestBody RentalRequest updates) {
        RentalRequest updatedRental = rentalService.partialUpdateRentalById(id, updates);
        return ResponseEntity.ok(updatedRental);
    }

}
