package com.seuprojeto.projeto_web.security.jwt;

import com.seuprojeto.projeto_web.enums.Role;

public record CreateUserDTO(String username, String password, Role role) {
  
}
