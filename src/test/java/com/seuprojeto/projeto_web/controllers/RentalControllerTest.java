package com.seuprojeto.projeto_web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.entities.RentalEntity;
import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.repositories.RentalRepository;
import com.seuprojeto.projeto_web.requests.RentalRequest;
import com.seuprojeto.projeto_web.services.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @MockBean
    private RentalRepository rentalRepository;

    @Test
    void getAllRentals_Success() throws Exception {
        RentalEntity rental1 = new RentalEntity();
        RentalEntity rental2 = new RentalEntity();
        when(rentalRepository.findAll()).thenReturn(Arrays.asList(rental1, rental2));

        mockMvc.perform(MockMvcRequestBuilders.get("/rentals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createRental_Success() throws Exception {
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setClientId(1L);
        rentalRequest.setVehicleId(1L);
        rentalRequest.setRentalDateTimeStart(LocalDateTime.parse("2024-11-15T10:00:00"));
        rentalRequest.setRentalDateTimeEnd(LocalDateTime.parse("2024-11-16T10:00:00"));

        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setClient(new ClientEntity()); // Supondo que o cliente e o veículo existam
        rentalEntity.setVehicle(new VehicleEntity());
        rentalEntity.setRentalDateTimeStart(rentalRequest.getRentalDateTimeStart());
        rentalEntity.setRentalDateTimeEnd(rentalRequest.getRentalDateTimeEnd());

        when(rentalService.createRental(rentalRequest)).thenReturn(rentalRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"clientId\": 1, \"vehicleId\": 1, \"rentalDateTimeStart\": \"2024-11-15T10:00:00\", \"rentalDateTimeEnd\": \"2024-11-16T10:00:00\" }"))
                .andExpect(status().isCreated()) // Espera um status 201 - Criado
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.vehicleId").value(1));
    }

    @Test
    void createRental_ClientNotFound() throws Exception {
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setClientId(999L); // Cliente que não existe
        rentalRequest.setVehicleId(1L);
        rentalRequest.setRentalDateTimeStart(LocalDateTime.parse("2024-11-15T10:00:00"));
        rentalRequest.setRentalDateTimeEnd(LocalDateTime.parse("2024-11-16T10:00:00"));

        when(rentalService.createRental(rentalRequest)).thenThrow(new EntityNotFoundException("Client not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"clientId\": 999, \"vehicleId\": 1, \"rentalDateTimeStart\": \"2024-11-15T10:00:00\", \"rentalDateTimeEnd\": \"2024-11-16T10:00:00\" }"))
                .andExpect(status().isNotFound()) // Espera um status 404 - Não Encontrado
                .andExpect(jsonPath("$.message").value("Client not found"));
    }

    @Test
    void createRental_CnhExpired() throws Exception {
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setClientId(1L); // Cliente com CNH expirada
        rentalRequest.setVehicleId(1L);
        rentalRequest.setRentalDateTimeStart(LocalDateTime.parse("2024-11-15T10:00:00"));
        rentalRequest.setRentalDateTimeEnd(LocalDateTime.parse("2024-11-16T10:00:00"));

        when(rentalService.createRental(rentalRequest)).thenThrow(new DuplicateRegisterException("Client's CNH is expired"));

        mockMvc.perform(MockMvcRequestBuilders.post("/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"clientId\": 1, \"vehicleId\": 1, \"rentalDateTimeStart\": \"2024-11-15T10:00:00\", \"rentalDateTimeEnd\": \"2024-11-16T10:00:00\" }"))
                .andExpect(status().isConflict()) // Espera um status 409 - Conflito
                .andExpect(jsonPath("$.message").value("Client's CNH is expired"));
    }
}
