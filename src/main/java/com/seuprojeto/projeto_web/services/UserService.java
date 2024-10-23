package com.seuprojeto.projeto_web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.RoleEntity;
import com.seuprojeto.projeto_web.entities.UserEntity;
import com.seuprojeto.projeto_web.repositories.UserRepository;
import com.seuprojeto.projeto_web.security.CreateUserDTO;
import com.seuprojeto.projeto_web.security.LoginUserDTO;
import com.seuprojeto.projeto_web.security.ModelUserDetailsImpl;
import com.seuprojeto.projeto_web.security.SecurityConfig;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenDTO;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenService;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public void saveUser(CreateUserDTO createUserDto) {
        UserEntity newUser = UserEntity.builder()
                .username(createUserDto.username())
                .password(securityConfig.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(RoleEntity.builder().name(createUserDto.role()).build()))
                .build();

        userRepository.save(newUser);
    }

    public JwtTokenDTO authenticateUser(LoginUserDTO loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.username(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }
}
