package com.seuprojeto.projeto_web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seuprojeto.projeto_web.entities.RentalEntity;
import com.seuprojeto.projeto_web.entities.VehicleEntity;

public interface RentalRepository extends JpaRepository<RentalEntity, Long>{
    // Verificar se existem locações ativas para um cliente
    boolean existsByClientIdAndIsActiveTrue(Long clientId);

    default boolean existsByVehicleIdAndDataFimIsNull(Long veiculoId) {
        return false;
    }

    boolean existsByVehicleAndIsActiveTrue(VehicleEntity vehicle);

    boolean existsByIdAndIsActiveTrue(Long rentalId);

    List<RentalEntity> findByClientId(Long id);

    List<RentalEntity> findAllByClientIdAndIsActiveFalse(Long clientId);

}
