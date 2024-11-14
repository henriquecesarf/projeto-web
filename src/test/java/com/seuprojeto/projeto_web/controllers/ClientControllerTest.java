package com.seuprojeto.projeto_web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.MvcResult;
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
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registra o módulo para suportar LocalDate
    }

    @Test
    void getAllClients_Success() throws Exception {
        // Retorna uma lista de clientes com sucesso
        ClientRequest client1 = new ClientRequest(
                "Lucas",
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

        ClientRequest client2 = new ClientRequest(
                "Maria",
                "Silva",
                "98765432100",
                "maria@example.com",
                Sexo.F,
                LocalDate.of(1995, 5, 5),
                "CNH54321",
                CnhCategory.B,
                LocalDate.of(2023, 5, 5),
                "87654-321",
                "Outro Endereço",
                "Brasil",
                "Rio de Janeiro",
                "RJ",
                "Complemento"
        );

        when(clientService.findAllClients()).thenReturn(List.of(client1, client2));

        mockMvc.perform(get("/api/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void createClient_EmptyCpf_ReturnsBadRequest() throws Exception {
        // Dados inválidos - CPF vazio
        ClientRequest invalidClient = new ClientRequest(
                "Lucas",
                "Bueno",
                "",
                "lucas@example.com",
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

        MvcResult result = mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Verifique se a resposta contém a mensagem correta
        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF deve ser válido"));
    }


    @Test
    void createClient_WrongCpf_ReturnsBadRequest() throws Exception {
        // Dados inválidos - CPF inválido
        ClientRequest invalidClient = new ClientRequest(
                "Karol",
                "Lene",
                "12345432102",
                "Karol@example.com",
                Sexo.F,
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

        MvcResult result = mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andReturn();


        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF deve ser válido"));
    }

    @Test
    void getAllClients_EmptyTable_ReturnsEmptyList() throws Exception {
        // retorna uma lista vazia ao não haver clientes cadastrados
        when(clientService.findAllClients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/client"))
                .andExpect(status().isNoContent()); // Espera um status 204
    }

    @Test
    void getById_Success_ReturnsClient() throws Exception {
        // controlador retorna uma categoria específica ao receber um ID válido.
        ClientRequest client = new ClientRequest(
                "lene",
                "Daane",
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
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists())
                .andExpect(jsonPath("$.email").exists());
    }

        @Test
    void createClient_InvalidData_ReturnsBadRequest() throws Exception {
        // Dados inválidos - nome vazio
        ClientRequest invalidClient = new ClientRequest(
                "",
                "Doe",
                "09876543210",
                "jane@example.com",
                Sexo.F,
                LocalDate.of(1992, 5, 15),
                "CNH54321",
                CnhCategory.B,
                LocalDate.of(2025, 5, 15),
                "87654-321",
                "Another Address",
                "Brazil",
                "Rio de Janeiro",
                "RJ",
                "Apartment 2"
        );

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest());
    }

}
