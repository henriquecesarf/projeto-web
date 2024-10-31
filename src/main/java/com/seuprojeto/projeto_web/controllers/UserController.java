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

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUser(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.authenticateUser(loginUserDto);
        auditLogService.log("User", "Usúario com username: " + loginUserDto.username() + " logou no sistema.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody CreateUserDTO createUserDto) {
        userService.saveUser(createUserDto);
        auditLogService.logAdd("User", createUserDto.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
