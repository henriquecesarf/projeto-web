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

    @Operation(
            summary = "Get todas os alugueis",
            description = "Endpoint para consultar todas os alugueis disponíveis.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todas as alugueis"),
    })
    @GetMapping
    public ResponseEntity<List<RentalRequest>> getAllRentals() throws TableEmptyException {
        List<RentalRequest> rentals = rentalService.findAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @Operation(
            summary = "Get por ID de aluguel",
            description = "Endpoint para obter uma aluguel em específico.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Retorna um Json com os dados do aluguel consultado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalRequest> getById(@PathVariable Long id) throws EntityNotFoundException {
        RentalRequest rental = rentalService.findRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @Operation(
            summary = " Cadastro um aluguel",
            description = "Endpoint de cadastro de aluguel"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Retorna um Json com o objeto da aluguel cadastrado"),
    })
    @PostMapping
    public ResponseEntity<RentalRequest> postRental(@Valid @RequestBody RentalRequest rentalRequest) throws EntityNotFoundException, DuplicateRegisterException {
        RentalRequest rental = rentalService.createRental(rentalRequest) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }

    @Operation(
            summary = "Delete de aluguel",
            description = "Endpoint para fazer exclusão de um aluguel.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ""),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRentalbyId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Alteração de aluguel por put",
            description = "Endpoint para fazer a alteração de uma Aluguel pelo ID.\n\n"+
                    "É necessário especificar todos os campos do aluguel, tanto alterados, como não alterados. "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com os dados da aluguel Alterada"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<RentalRequest> putRental(@PathVariable Long id,@Valid  @RequestBody RentalRequest rentalRequest) {
        RentalRequest updatedRental = rentalService.updateRentalById(id, rentalRequest);
        return ResponseEntity.ok(updatedRental);
    }

    @Operation(
            summary = "Alteração de aluguel por patch",
            description = "Endpoint para fazer a alteração parcial no Aluguel pelo ID.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com os dados do aluguel Alterado"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<RentalRequest> patchRental(@PathVariable Long id,@RequestBody RentalRequest updates) {
        RentalRequest updatedRental = rentalService.partialUpdateRentalById(id, updates);
        return ResponseEntity.ok(updatedRental);
    }

    @Operation(
            summary = "Get histórico por ID do Cliente",
            description = "Endpoint para obter o histórico de locação de um cliente em específico.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Retorna um Json com os dados do histórico de alugueis consultados"),
    })
    @GetMapping("/history/{id}")
    public ResponseEntity<List<RentalEntity>> getRentalHistory(@PathVariable Long id) throws TableEmptyException {
        List<RentalEntity> rentals = rentalService.getRentalHistoryById(id);
        return ResponseEntity.ok(rentals);
    }

    // Endpoint para adicionar um sinistro a uma locação
    @Operation(
            summary = "Post para cadastrar um sinistro a um aluguel",
            description = "Endpoint de cadastro de um sinistro a um aluguel através do ID do sinistro\n" +
                    " São dados necessarios para o processo de cadadstro:\n" +
                        "- ID do sinistro que deseja cadastrar\n" +
                        "- ID do aluguel que deseja vincular"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Retorna um Json com o objeto com o sinistro cadastrado"),
    })
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
        @ApiResponse(responseCode = "200", description = "Retorna o Json da Locação criada."),
    })
    @PostMapping("/checkout/{rentalId}")
    public ResponseEntity<String> checkoutRental(@PathVariable Long rentalId,@Valid @RequestBody RentalCheckoutRequest rentalCheckoutRequest) throws EntityNotFoundException, DuplicateRegisterException, FieldInvalidException {
        return ResponseEntity.ok(rentalService.checkoutRental(rentalId, rentalCheckoutRequest, 1));
    }

    @Operation(
            summary = "Post para calcular o valor a pagar de um aluguel",
            description = "Endpoint de calculo do valor total a pagar de um aluguel através do ID do aluguel.\n" +
                    "O calculo é feito através da soma do valor do veículo com a soma do valor de todos os sinistros, mais o valor total dos adicionais e o valor total das diárias.\n" +
                    " São dados necessarios para o processo de cadadstro:\n" +
                    "- ID do aluguel que deseja vincular"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Retorna um Json com o valor total a ser pago e dados do aluguel"),
    })
    @PostMapping("/value-to-pay/{rentalId}")
    public ResponseEntity<String> calculateValueToPay(@PathVariable Long rentalId,@Valid @RequestBody RentalCheckoutRequest rentalCheckoutRequest) throws EntityNotFoundException, DuplicateRegisterException, FieldInvalidException {
        return ResponseEntity.ok(rentalService.checkoutRental(rentalId, rentalCheckoutRequest, 2));
    }

}
