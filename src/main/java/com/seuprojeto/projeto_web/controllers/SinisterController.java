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

import com.seuprojeto.projeto_web.entities.SinisterEntity;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.requests.SinisterRequest;
import com.seuprojeto.projeto_web.services.SinisterService;

@RestController
@RequestMapping("api/sinister")
public class SinisterController {

    @Autowired
    private SinisterService sinisterService;

    @Operation(
            summary = "Get todos os sinistros",
            description = "Endpoint para consultar todas os sinistros cadastrados.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todos os sinistros"),
    })
    @GetMapping
    public ResponseEntity<List<SinisterEntity>> getAllSinister() throws TableEmptyException {
        List<SinisterEntity> sinisters = sinisterService.findAllSinisters();
        return ResponseEntity.ok(sinisters);
    }

    @Operation(
            summary = "Get por ID de sinistro",
            description = "Endpoint para obter os dados de um sinistros em específico.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Retorna um Json com os dados do sinistro consultado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SinisterRequest> getById(@PathVariable Long id) throws EntityNotFoundException {
        SinisterRequest sinister = sinisterService.findSinisterById(id);
        return ResponseEntity.ok(sinister);
    }

}
