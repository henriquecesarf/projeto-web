package com.seuprojeto.projeto_web.security.jwt;

import java.util.List;

import com.seuprojeto.projeto_web.enums.Role;

public record UserDTO(Long id, String username, List<Role> roles) {
  
}
