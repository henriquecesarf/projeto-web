package com.seuprojeto.projeto_web.repositories;

import com.seuprojeto.projeto_web.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByPlaca(String placa);

    @Query("SELECT v FROM VehicleEntity v WHERE NOT EXISTS (SELECT r FROM RentalEntity r WHERE r.vehicle.id = v.id AND r.rentalDateTimeEnd IS NULL)")
    List<VehicleEntity> findAvailableVehiclesForRent();
}
