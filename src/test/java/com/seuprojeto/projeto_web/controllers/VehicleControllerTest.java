package com.seuprojeto.projeto_web.controllers;

import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.enums.Exchange;
import com.seuprojeto.projeto_web.requests.VehicleRequest;
import com.seuprojeto.projeto_web.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService veiculoService;

    // Teste de criação de veículo
    @Test
    void testPostVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest(
                "Carro Teste", "Fabricante X", "Versão 2023", "https://fipe.com",
                "ABC1234", "Preto", Exchange.AUTOMATIC, 10000.0, 5, 300,
                true, List.of("Ar Condicionado", "Direção Hidráulica"), 100.0, 1L
        );

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(1L); // Simula que o veículo foi registrado com ID 1

        given(veiculoService.registerVehicle(any(VehicleRequest.class))).willReturn(vehicleEntity);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Carro Teste\", \"manufacturer\": \"Fabricante X\", \"version\": \"Versão 2023\", \"urlFipe\": \"https://fipe.com\", \"plate\": \"ABC1234\", \"color\": \"Preto\", \"exchange\": \"AUTOMATIC\", \"km\": 10000.0, \"capacityPassengers\": 5, \"volumeLoad\": 300, \"available\": true, \"accessories\": [\"Ar Condicionado\", \"Direção Hidráulica\"], \"valuedaily\": 100.0, \"categoryId\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1))) // Verifica se o ID foi retornado
                .andExpect(jsonPath("$.name", is("Carro Teste"))) // Verifica o nome
                .andExpect(jsonPath("$.manufacturer", is("Fabricante X"))); // Verifica o fabricante
    }

    // Teste de atualização de veículo
    @Test
    void testEditVehicle() throws Exception {
        Long vehicleId = 1L;
        VehicleEntity updatedVehicle = new VehicleEntity();
        updatedVehicle.setId(vehicleId);
        updatedVehicle.setName("Carro Atualizado");

        given(veiculoService.editVehicle(eq(vehicleId), any(VehicleEntity.class))).willReturn(updatedVehicle);

        mockMvc.perform(put("/api/vehicles/{id}", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Carro Atualizado\", \"manufacturer\": \"Fabricante Y\", \"version\": \"Versão 2024\", \"urlFipe\": \"https://fipe.com\", \"plate\": \"ABC1234\", \"color\": \"Azul\", \"exchange\": \"MANUAL\", \"km\": 15000.0, \"capacityPassengers\": 5, \"volumeLoad\": 350, \"available\": true, \"accessories\": [\"Ar Condicionado\", \"Som\"], \"valuedaily\": 120.0, \"categoryId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(vehicleId.intValue())))
                .andExpect(jsonPath("$.name", is("Carro Atualizado")));
    }

    // Teste de deleção de veículo
    @Test
    void testDeleteVehicle() throws Exception {
        Long vehicleId = 1L;

        doNothing().when(veiculoService).deleteVehicleById(vehicleId);

        mockMvc.perform(delete("/api/vehicles/{id}", vehicleId))
                .andExpect(status().isNoContent()); // Verifica se o status é 204 (sem conteúdo)
    }

    // Teste de listagem de veículos
    @Test
    void testListVehicles() throws Exception {
        List<VehicleEntity> vehicles = Arrays.asList(
                new VehicleEntity(), new VehicleEntity()
        );

        given(veiculoService.listVehicles()).willReturn(vehicles);

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // Verifica que retornou 2 veículos
    }

    // Teste de veículos disponíveis
    @Test
    void testAvailableVehicles() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        List<VehicleEntity> availableVehicles = Arrays.asList(new VehicleEntity());

        given(veiculoService.vehiclesAvailableForRent(startDate, endDate)).willReturn(availableVehicles);

        mockMvc.perform(get("/api/vehicles/disponiveis")
                        .param("inicio", startDate.toString())
                        .param("fim", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // Verifica que retornou 1 veículo disponível
    }

    @Test
    void postVehicle() {
    }

    @Test
    void editVehicle() {
    }

    @Test
    void deleteVehicle() {
    }

    @Test
    void listarVeiculos() {
    }

    @Test
    void veiculosDisponiveis() {
    }
}
