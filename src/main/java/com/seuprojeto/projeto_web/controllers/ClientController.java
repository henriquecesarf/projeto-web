package com.seuprojeto.projeto_web.controllers;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.ClientRequest;
import com.seuprojeto.projeto_web.services.ClientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(
            summary = "Get todos os clientes",
            description = "Endpoint para consultar todas os clientes cadastrados.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todos os clientes"),
    })
    @GetMapping
    public ResponseEntity<List<ClientRequest>> getAllClients() throws TableEmptyException {
        List<ClientRequest> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(
            summary = "Get por ID de cliente",
            description = "Endpoint para obter os dados de um cliente em específica.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Retorna um Json com os dados do cliente consultada"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientRequest> getById(@PathVariable Long id) throws EntityNotFoundException {
        ClientRequest client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Cadastro de cliente",
            description = "Endpoint de cadastro de clientes"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Retorna um Json com o objeto do cliente cadastrado"),
    })
    @PostMapping
    public ResponseEntity<ClientRequest> postClient(@Validated @RequestBody ClientRequest clientRequest) throws DuplicateRegisterException {
        ClientRequest newClient = clientService.createClient(clientRequest);
        return ResponseEntity.ok(newClient);
    }

    @Operation(
            summary = "Delete de clientes",
            description = "Endpoint para fazer Exclusão de um cliente.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ""),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClientbyId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Alteração de clientes",
            description = "Endpoint para fazer a alteração de um cliente pelo ID.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com os dados da cliente alterado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientRequest> updateClient(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        ClientRequest updatedClient = clientService.updateClientById(id, clientRequest);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(
            summary = "Alteração de cliente por patch",
            description = "Endpoint para fazer a alteração parcial no cliente pelo ID.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com os dados do cliente Alterada"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ClientRequest> partialUpdateClient(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ClientRequest updatedClient = clientService.partialUpdateClientById(id, updates);
        return ResponseEntity.ok(updatedClient);
    }
}