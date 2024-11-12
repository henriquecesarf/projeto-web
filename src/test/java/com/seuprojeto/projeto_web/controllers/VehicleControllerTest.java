package com.seuprojeto.projeto_web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.enums.Exchange;
import com.seuprojeto.projeto_web.requests.VehicleRequest;
import com.seuprojeto.projeto_web.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void listarVeiculos_Success() throws Exception {
        // Criando um veículo para o teste
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(1L);
        vehicle.setName("Carro Teste");
        vehicle.setManufacturer("Fabricante X");
        vehicle.setVersion("Versão 1.0");
        vehicle.setUrlFipe("http://fipe.url");
        vehicle.setPlate("ABC1234");
        vehicle.setColor("Preto");
        vehicle.setExchange(Exchange.MANUAL);
        vehicle.setKm(1000.0);
        vehicle.setCapacityPassengers(5);
        vehicle.setVolumeLoad(500);
        vehicle.setAvailable(true);
        vehicle.setAccessories(List.of("Ar-condicionado", "Direção hidráulica"));
        vehicle.setValuedaily(150.0);
        vehicle.setRegistrationDate(LocalDateTime.now());

        when(vehicleService.listVehicles()).thenReturn(List.of(vehicle));

        mockMvc.perform(get("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Carro Teste"))
                .andExpect(jsonPath("$[0].manufacturer").value("Fabricante X"))
                .andExpect(jsonPath("$[0].plate").value("ABC1234"));
    }

    @Test
    void listarVeiculos_EmptyTable_ReturnsNoContent() throws Exception {
        // Simulando uma lista vazia de veículos
        when(vehicleService.listVehicles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void veiculosDisponiveis_Success() throws Exception {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(1L);
        vehicle.setName("Carro Disponível");
        vehicle.setManufacturer("Fabricante Z");
        vehicle.setPlate("GHI1234");
        vehicle.setAvailable(true);

        when(vehicleService.vehiclesAvailableForRent(any(), any())).thenReturn(List.of(vehicle));

        mockMvc.perform(get("/api/vehicles/disponiveis")
                        .param("inicio", "2024-12-01")
                        .param("fim", "2024-12-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Carro Disponível"))
                .andExpect(jsonPath("$[0].manufacturer").value("Fabricante Z"))
                .andExpect(jsonPath("$[0].plate").value("GHI1234"));
    }

    @Test
    void postVehicle_Success() throws Exception {
        // Mock do request e do resultado esperado
        VehicleRequest request = new VehicleRequest(
                "Carro Teste", "Fabricante X", "Versão 1", "https://urlFipe.com",
                "ABC1234", "Preto", Exchange.AUTOMATIC, 5000.0, 5, 300,
                true, List.of("Ar-condicionado", "Direção Hidráulica"), 200.0, 1L);

        VehicleEntity expectedResponse = new VehicleEntity();
        expectedResponse.setId(1L);
        expectedResponse.setName(request.getName());

        // Configura o mock para retornar o veículo criado
        when(vehicleService.registerVehicle(any(VehicleRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Carro Teste"));
    }
    @Test
    void deleteVehicle_Success() throws Exception {
        Long vehicleId = 1L;

        // Configura o mock para não lançar exceção
        doNothing().when(vehicleService).deleteVehicleById(vehicleId);

        mockMvc.perform(delete("/api/vehicles/{id}", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
