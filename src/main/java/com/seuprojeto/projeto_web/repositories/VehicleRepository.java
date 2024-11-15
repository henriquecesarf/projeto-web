package com.seuprojeto.projeto_web.repositories;

import com.seuprojeto.projeto_web.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByPlate(String plate);

    @Query("SELECT v FROM VehicleEntity v WHERE NOT EXISTS (SELECT r FROM RentalEntity r WHERE r.vehicle.id = v.id AND r.rentalDateTimeEnd IS NULL)")
    List<VehicleEntity> findAvailableVehiclesForRent();

    List<VehicleEntity> findByAvailableTrue();

    public Optional<VehicleEntity> findByIdAndAvailableTrue(Long id);

    @Query("SELECT o.plate FROM VehicleEntity o WHERE o.id = :vehicleId")
    String findPlateById(@Param("vehicleId") Long vehicleId);

}
