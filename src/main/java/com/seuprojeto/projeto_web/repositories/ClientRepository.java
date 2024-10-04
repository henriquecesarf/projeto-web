package com.seuprojeto.projeto_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seuprojeto.projeto_web.entities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    //buscar um cliente pelo CPF
    ClientEntity findByCpf(String cpf);
    //buscar um cliente pelo CPF
    ClientEntity findByEmail(String email);
    //buscar um cliente pelo CPF
    ClientEntity findByCnh(String cnh);
}
