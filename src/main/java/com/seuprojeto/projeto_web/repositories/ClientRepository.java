package com.seuprojeto.projeto_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.seuprojeto.projeto_web.entities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    //buscar um cliente pelo CPF
    ClientEntity findByCpf(String cpf);
    //buscar um cliente pelo CPF
    ClientEntity findByEmail(String email);
    //buscar um cliente pelo CPF
    ClientEntity findByCnh(String cnh);

    ClientEntity findByCnpj(String cnpj); // Método para buscar pelo CNPJ
;
    // Método para buscar um cliente pelo ID e st_excluido como false
    ClientEntity findByIdAndStExcluidoFalse(Long id);    
    // Método para buscar clientes com st_excluido como false
    List<ClientEntity> findByStExcluidoFalse();

    boolean existsByIdAndCnpjNull(Long id);
}
