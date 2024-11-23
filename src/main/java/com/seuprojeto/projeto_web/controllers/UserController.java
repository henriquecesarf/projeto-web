package com.seuprojeto.projeto_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seuprojeto.projeto_web.security.CreateUserDTO;
import com.seuprojeto.projeto_web.security.LoginUserDTO;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenDTO;
import com.seuprojeto.projeto_web.services.AuditLogService;
import com.seuprojeto.projeto_web.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuditLogService auditLogService;

    @Operation(
            summary = "Endpoint para logar na api",
            description = "É necessario logar para poder gerar o token e utilizar nas requisições.\n\n"
    )
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUser(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.authenticateUser(loginUserDto);
        auditLogService.log("User", "Usúario com username: " + loginUserDto.username() + " logou no sistema.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Operation(
            summary = "Endpoint para cadastrar um User",
            description = "No campo 'role' só é permitido os valores 'ROLE_USER'(para usuarios comuns) e 'ROLE_ADMIN'(para usuarios administrativos).\n\n"
    )
    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody CreateUserDTO createUserDto) {
        userService.saveUser(createUserDto);
        auditLogService.logAdd("User", createUserDto.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
