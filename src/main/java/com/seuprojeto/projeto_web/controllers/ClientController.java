package com.seuprojeto.projeto_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.ClientRequest;
import com.seuprojeto.projeto_web.services.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAllClients() throws TableEmptyException {
        List<ClientEntity> client = clientService.findAllClients();
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientRequest> createCategory(@RequestBody ClientRequest clientRequest) {
        ClientRequest newClient = clientService.createCategory(clientRequest);
        return ResponseEntity.ok(newClient);
    }

}
