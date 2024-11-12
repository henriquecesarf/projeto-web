package com.seuprojeto.projeto_web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seuprojeto.projeto_web.enums.CnhCategory;
import com.seuprojeto.projeto_web.enums.Sexo;
import com.seuprojeto.projeto_web.requests.ClientRequest;
import com.seuprojeto.projeto_web.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void getAllClients_Success() throws Exception {
        //retorna uma lista de clientes com sucesso
        ClientRequest client = new ClientRequest(
                "Galvão",
                "Bueno",
                "12345678901",
                "john@example.com",
                Sexo.M,
                LocalDate.of(1990, 1, 1),
                "CNH12345",
                CnhCategory.A,
                LocalDate.of(2025, 1, 1),
                "12345-678",
                "Endereço Exemplo",
                "Brasil",
                "São Paulo",
                "SP",
                "Complemento"
        );

        when(clientService.findAllClients()).thenReturn(List.of(client));

        mockMvc.perform(get("/api/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Galvão"));
    }

    @Test
    void getAllClients_EmptyTable_ReturnsEmptyList() throws Exception {
        //retorna uma lista vazia ao não haver clientes cadastrados
        when(clientService.findAllClients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/client"))
                .andExpect(status().isNoContent()); // Espera um status 204
    }

    @Test
    void getById_Success() throws Exception {
        //controlador retorna uma categoria específica ao receber um ID válido.
        ClientRequest client = new ClientRequest(
                "John Doe",
                "Doe",
                "12345678901",
                "john@example.com",
                Sexo.M,
                LocalDate.of(1990, 1, 1),
                "1234567890",
                CnhCategory.B,
                LocalDate.of(2025, 12, 31),
                "12345-678",
                "Street 123",
                "Brazil",
                "City",
                "State",
                "Apartment 1"
        );

        when(clientService.findClientById(anyLong())).thenReturn(client);

        mockMvc.perform(get("/api/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.surname").value("Doe")) // Verifica o sobrenome
                .andExpect(jsonPath("$.email").value("john@example.com")); // Verifica o email
    }
}
