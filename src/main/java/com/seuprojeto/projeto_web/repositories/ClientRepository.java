package com.seuprojeto.projeto_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.seuprojeto.projeto_web.entities.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>{

}
