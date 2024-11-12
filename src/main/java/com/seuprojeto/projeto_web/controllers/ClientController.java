package com.seuprojeto.projeto_web.controllers;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<List<ClientRequest>> getAllClients() throws TableEmptyException {
        List<ClientRequest> clients = clientService.findAllClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se a lista estiver vazia
        }
        return ResponseEntity.ok(clients); // Retorna 200 com a lista se não estiver vazia
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientRequest> getById(@PathVariable Long id) throws EntityNotFoundException {
        ClientRequest client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientRequest> postClient(@Validated @RequestBody ClientRequest clientRequest) throws DuplicateRegisterException {
        ClientRequest newClient = clientService.createClient(clientRequest);
        return ResponseEntity.ok(newClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClientbyId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientRequest> updateClient(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        ClientRequest updatedClient = clientService.updateClientById(id, clientRequest);
        return ResponseEntity.ok(updatedClient);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientRequest> partialUpdateClient(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ClientRequest updatedClient = clientService.partialUpdateClientById(id, updates);
        return ResponseEntity.ok(updatedClient);
    }
}
