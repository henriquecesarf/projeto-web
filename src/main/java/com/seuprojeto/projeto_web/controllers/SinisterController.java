package com.seuprojeto.projeto_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.entities.SinisterEntity;
import com.seuprojeto.projeto_web.exceptions.FieldNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.SinisterRequest;
import com.seuprojeto.projeto_web.services.SinisterService;

@RestController
@RequestMapping("api/sinister")
public class SinisterController {

    @Autowired
    private SinisterService sinisterService;

    @GetMapping
    public ResponseEntity<List<SinisterEntity>> getAllSinister() throws TableEmptyException {
        List<SinisterEntity> sinisters = sinisterService.findAllSinisters();
        return ResponseEntity.ok(sinisters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinisterRequest> getById(@PathVariable Long id) throws FieldNotFoundException {
        SinisterRequest sinister = sinisterService.findSinisterById(id);
        return ResponseEntity.ok(sinister);
    }

}
