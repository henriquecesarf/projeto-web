package com.seuprojeto.projeto_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seuprojeto.projeto_web.entities.RentalEntity;
import com.seuprojeto.projeto_web.entities.RentalSinister;
import com.seuprojeto.projeto_web.entities.SinisterEntity;


public interface RentalSinisterRepository extends JpaRepository<RentalSinister, Long> {
    boolean existsByRentalAndSinister(RentalEntity rental, SinisterEntity sinister);

    boolean existsByRentalId(Long rentalId);

    boolean existsByRentalIdAndSinisterId(Long rentalId, int sinisterId);
}
