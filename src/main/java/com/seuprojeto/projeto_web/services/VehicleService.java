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
            throw new DuplicateRegisterException("Veículo com está placa já cadastrado");
        }
        if (!categoryRepository.existsById(veiculo.getCategoryId())) {
            throw new EntityNotFoundException("Categoria com ID " + veiculo.getCategoryId() + " não existe.");
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
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        boolean isRent = rentalRepository.existsByVehicleIdAndDataFimIsNull(id);

        if (isRent) {
            throw new RuntimeException("Não é possível editar um veículo que está com aluguel em curso");
        }

        BeanUtils.copyProperties(veiculoAtualizado, veiculo, "id");


        return vehicleRepository.save(veiculo);
    }

    @CacheEvict(value = "vehicle", allEntries = true)
    public void deleteVehicleById(Long id) {
        VehicleEntity veiculo = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        
        boolean isRent = rentalRepository.existsByVehicleIdAndDataFimIsNull(id);
        
        if (isRent) {
            throw new RuntimeException("Não é possível editar um veículo que está com aluguel em curso");
        }

        vehicleRepository.delete(veiculo);
    }

    @Cacheable(value = "vehicle", key = "'all_vehicle'")
    public List<VehicleEntity> listVehicles() {
        return vehicleRepository.findAll();
    }

    @Cacheable(value = "vehicle", key = "'#id'")
    public List<VehicleEntity> vehiclesAvailableForRent
            (LocalDate start, LocalDate end) {
        return vehicleRepository.findAvailableVehiclesForRent();
    }
}
