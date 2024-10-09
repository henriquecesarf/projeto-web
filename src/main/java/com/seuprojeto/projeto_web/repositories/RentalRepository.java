package com.seuprojeto.projeto_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seuprojeto.projeto_web.entities.RentalEntity;

public interface RentalRepository extends JpaRepository<RentalEntity, Long>{
    // Verificar se existem locações ativas para um cliente
    boolean existsByClientIdAndIsActiveTrue(Long clientId);
}
