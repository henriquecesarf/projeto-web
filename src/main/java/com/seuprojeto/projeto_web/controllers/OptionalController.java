package com.seuprojeto.projeto_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.entities.OptionalEntity;
import com.seuprojeto.projeto_web.exceptions.FieldNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.OptionalRequest;
import com.seuprojeto.projeto_web.services.OptionalService;


@RestController
@RequestMapping("api/optional")
public class OptionalController {

    @Autowired
    private OptionalService optionalService;

    @GetMapping
    public ResponseEntity<List<OptionalEntity>> getAllOptionals() throws TableEmptyException {
        List<OptionalEntity> optional = optionalService.findAllOptionals();
        return ResponseEntity.ok(optional);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionalRequest> getById(@PathVariable Long id) throws FieldNotFoundException {
        OptionalRequest optional = optionalService.findOptionalById(id);
        return ResponseEntity.ok(optional);
    }

}