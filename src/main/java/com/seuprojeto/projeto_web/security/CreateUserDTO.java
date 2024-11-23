package com.seuprojeto.projeto_web.security;

import com.seuprojeto.projeto_web.enums.Role;

public record CreateUserDTO(String username, String password, Role role) {
  
}
