package com.seuprojeto.projeto_web.requests;

import java.util.List;

import com.seuprojeto.projeto_web.enums.Role;

public record UserRequest(Long id, String username, List<Role> roles) {
  
}
