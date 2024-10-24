package com.seuprojeto.projeto_web.services;


import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.repositories.RentalRepository;
import com.seuprojeto.projeto_web.repositories.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

import com.seuprojeto.projeto_web.requests.*;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    private RentalRepository rentalRepository;
    ModelMapper modelMapper = new ModelMapper();


    public VehicleRequest registerVehicle(VehicleRequest veiculo) {
        if (vehicleRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new RuntimeException("Veículo já cadastrado com esta placa");
        }
        VehicleEntity vehicleEntity = modelMapper.map(vehicleRepository, VehicleEntity.class);
        vehicleRepository.save(vehicleEntity);

        return modelMapper.map(vehicleEntity, VehicleRequest.class);
    }

    public VehicleEntity editVehicle(Long id, VehicleEntity veiculoAtualizado) {
        VehicleEntity veiculo = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        boolean isRent = rentalRepository.existsByVehicleIdAndDataFimIsNull(id);

        if (isRent) {
            throw new RuntimeException("Não é possível editar um veículo que está com aluguel em curso");
        }

        BeanUtils.copyProperties(veiculoAtualizado, veiculo, "id");


        return vehicleRepository.save(veiculo);
    }

    public void deleteVehicle(Long id) {
        VehicleEntity veiculo = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        
        boolean isRent = rentalRepository.existsByVehicleIdAndDataFimIsNull(id);
        
        if (isRent) {
            throw new RuntimeException("Não é possível editar um veículo que está com aluguel em curso");
        }

        vehicleRepository.delete(veiculo);
    }

    public List<VehicleEntity> listVehicles() {
        return vehicleRepository.findAll();
    }

    public List<VehicleEntity> vehiclesAvailableForRent
            (LocalDate start, LocalDate end) {
        return vehicleRepository.findAvailableVehiclesForRent();
    }
}
