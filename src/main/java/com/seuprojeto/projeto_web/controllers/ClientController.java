package com.seuprojeto.projeto_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.exceptions.FieldInvalid;
import com.seuprojeto.projeto_web.exceptions.FieldNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.ClientRequest;
import com.seuprojeto.projeto_web.services.ClientService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAllClients() throws TableEmptyException {
        List<ClientEntity> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientRequest> getById(@PathVariable Long id) throws FieldNotFoundException {
        ClientRequest client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientRequest> createCategory(@Validated @RequestBody ClientRequest clientRequest) throws FieldInvalid {
        ClientRequest newClient = clientService.createCategory(clientRequest);
        return ResponseEntity.ok(newClient);
    }

}
