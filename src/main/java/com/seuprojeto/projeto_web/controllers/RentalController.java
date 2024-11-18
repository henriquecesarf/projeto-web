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

import com.seuprojeto.projeto_web.entities.RentalEntity;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.FieldInvalidException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.RentalCheckoutRequest;
import com.seuprojeto.projeto_web.requests.RentalRequest;
import com.seuprojeto.projeto_web.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    public ResponseEntity<RentalRequest> postRental(@Valid @RequestBody RentalRequest rentalRequest) throws EntityNotFoundException, DuplicateRegisterException {
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

    @GetMapping("/history/{id}")
    public ResponseEntity<List<RentalEntity>> getRentalHistory(@PathVariable Long id) throws TableEmptyException {
        List<RentalEntity> rentals = rentalService.getRentalHistoryById(id);
        return ResponseEntity.ok(rentals);
    }

    // Endpoint para adicionar um sinistro a uma locação
    @PostMapping("/{rentalId}/sinister/{sinisterId}")
    public ResponseEntity<String> adicionarSinistroARental(
            @PathVariable Long rentalId, 
            @PathVariable Long sinisterId) throws EntityNotFoundException, DuplicateRegisterException {

        rentalService.adicionarSinistroARental(rentalId, sinisterId);
        return ResponseEntity.ok("Sinistro adicionado à locação com sucesso");
    }

    @Operation(
    summary = "Finalizar locação",
    description = "Endpoint para realizar o checkout de uma locação. Ele calcula o valor final, verifica sinistros e atualiza o status.\n\n" +
                 "São dados necessarios para o processo de checkout:\n" +
                 "- Data final da locação no formato: 'AAAA-MM-DDTHH:MM'\n" +
                 "- Kilometragem de devolução do veiculo\n" +
                 "- Pagamento Efetuado pelo usuario\n" +
                 "- valor total somado de todos os sinistros cadastrados na locação(caso houver sinistro)\n" +
                 "- lista de objetos opcionais com id e quantidades dos opcionais perdidos, para atualização de inventário(caso houver sinistro de perda de opcionais)\n" +
                 "- Atualização do status da locação"
)
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Locação finalizada com sucesso."),
    })
    @PostMapping("/checkout/{rentalId}")
    public ResponseEntity<String> checkoutRental(@PathVariable Long rentalId,@Valid @RequestBody RentalCheckoutRequest rentalCheckoutRequest) throws EntityNotFoundException, DuplicateRegisterException, FieldInvalidException {
        return ResponseEntity.ok(rentalService.checkoutRental(rentalId, rentalCheckoutRequest, 1));
    }

    @PostMapping("/value-to-pay/{rentalId}")
    public ResponseEntity<String> calculateValueToPay(@PathVariable Long rentalId,@Valid @RequestBody RentalCheckoutRequest rentalCheckoutRequest) throws EntityNotFoundException, DuplicateRegisterException, FieldInvalidException {
        return ResponseEntity.ok(rentalService.checkoutRental(rentalId, rentalCheckoutRequest, 2));
    }

}
