package com.seuprojeto.projeto_web.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seuprojeto.projeto_web.security.jwt.CreateUserDTO;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenDTO;
import com.seuprojeto.projeto_web.security.jwt.LoginUserDTO;
import com.seuprojeto.projeto_web.security.jwt.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.autenticarUsuario(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody CreateUserDTO createUserDto) {
        userService.salvarUsuario(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
