package com.seuprojeto.projeto_web.services;


import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.repositories.CategoryRepository;
import com.seuprojeto.projeto_web.repositories.RentalRepository;
import com.seuprojeto.projeto_web.repositories.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.seuprojeto.projeto_web.requests.*;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    ModelMapper modelMapper = new ModelMapper();


    @CacheEvict(value = "vehicle", allEntries = true)
    public VehicleEntity registerVehicle(VehicleRequest veiculo) {
        if (vehicleRepository.findByPlate(veiculo.getPlate()).isPresent()) {
            throw new DuplicateRegisterException("Vehicle with this plate already registered");
        }
        if (!categoryRepository.existsById(veiculo.getCategoryId())) {
            throw new EntityNotFoundException("Category with ID " + veiculo.getCategoryId() + " does not exist.");
        }

        VehicleEntity vehicleEntity = modelMapper.map(veiculo, VehicleEntity.class);

        // Verifica se o ID já está definido (não nulo) e, se estiver, define como null para garantir inserção
        if (vehicleEntity.getId() != null) {
            vehicleEntity.setId(null); // Garante que o Hibernate faça a inserção e não a atualização
        }

        // Verifica se a data de registro não foi fornecida e define como a data atual
        if (vehicleEntity.getRegistrationDate() == null) {
            vehicleEntity.setRegistrationDate(LocalDateTime.now()); // Define a data atual
        }
      
        vehicleRepository.save(vehicleEntity);

        return vehicleEntity;
    }

    @CacheEvict(value = "vehicle", allEntries = true)
    public VehicleEntity editVehicle(Long id, VehicleEntity veiculoAtualizado) {
        VehicleEntity veiculo = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean isRent = rentalRepository.existsByVehicleIdAndDataFimIsNull(id);

        if (isRent) {
            throw new RuntimeException("It is not possible to edit a vehicle that is currently on lease.");
        }

        BeanUtils.copyProperties(veiculoAtualizado, veiculo, "id");


        return vehicleRepository.save(veiculo);
    }

    @CacheEvict(value = "vehicle", allEntries = true)
    public void deleteVehicleById(Long id) {
        VehicleEntity veiculo = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        boolean isRent = rentalRepository.existsByVehicleIdAndDataFimIsNull(id);
        
        if (isRent) {
            throw new RuntimeException("It is not possible to edit a vehicle that is currently on lease.");
        }

        vehicleRepository.delete(veiculo);
    }

    @Cacheable(value = "vehicle", key = "'all_vehicle'")
    public List<VehicleEntity> listVehicles() {
        return vehicleRepository.findAll();
    }

    @Cacheable(value = "vehicle", key = "'#start.toString() + #end.toString()'")
    public List<VehicleEntity> vehiclesAvailableForRent(LocalDateTime start, LocalDateTime end) {
        // A data final é ajustada para o último minuto do dia
        LocalDateTime endDateTime = end.plusMinutes(1);  // Para considerar até o último minuto do dia final

        return vehicleRepository.findAvailableVehiclesForRent(start, endDateTime);
    }
}
