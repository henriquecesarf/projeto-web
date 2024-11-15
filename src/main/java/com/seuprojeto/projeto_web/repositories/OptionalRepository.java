package com.seuprojeto.projeto_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seuprojeto.projeto_web.entities.OptionalEntity;

public interface OptionalRepository extends JpaRepository<OptionalEntity, Long>{

    @Query("SELECT o.valueLocation FROM OptionalEntity o WHERE o.id = :optionalId")
    Optional<Double> findValueLocationById(@Param("optionalId") Long optionalId);

}
