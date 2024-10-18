package com.seuprojeto.projeto_web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seuprojeto.projeto_web.security.jwt.ModelUser;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {

    Optional<ModelUser> findByUsername(String username);

}
