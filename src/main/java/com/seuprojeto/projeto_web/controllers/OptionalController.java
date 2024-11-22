package com.seuprojeto.projeto_web.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.projeto_web.entities.OptionalEntity;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.OptionalRequest;
import com.seuprojeto.projeto_web.services.OptionalService;


@RestController
@RequestMapping("api/optional")
public class OptionalController {

    @Autowired
    private OptionalService optionalService;

    @Operation(
            summary = "Get todos os opcionais",
            description = "Endpoint para consultar todas os opcionais cadastrados.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todos os opcionais"),
    })
    @GetMapping
    public ResponseEntity<List<OptionalEntity>> getAllOptionals() throws TableEmptyException {
        List<OptionalEntity> optional = optionalService.findAllOptionals();
        return ResponseEntity.ok(optional);
    }

    @Operation(
            summary = "Get por ID de opcional",
            description = "Endpoint para obter os dados de um opcional em específico.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Retorna um Json com os dados do opcional consultado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<OptionalRequest> getById(@PathVariable Long id) throws EntityNotFoundException {
        OptionalRequest optional = optionalService.findOptionalById(id);
        return ResponseEntity.ok(optional);
    }

}