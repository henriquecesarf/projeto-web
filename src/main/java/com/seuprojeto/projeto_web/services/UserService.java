package com.seuprojeto.projeto_web.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.RoleEntity;
import com.seuprojeto.projeto_web.entities.UserEntity;
import com.seuprojeto.projeto_web.exceptions.FieldNotFoundException;
import com.seuprojeto.projeto_web.repositories.UserRepository;
import com.seuprojeto.projeto_web.security.CreateUserDTO;
import com.seuprojeto.projeto_web.security.LoginUserDTO;
import com.seuprojeto.projeto_web.security.ModelUserDetailsImpl;
import com.seuprojeto.projeto_web.security.SecurityConfig;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenDTO;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    HttpSession session;

    public UserEntity findByUsername(String username){
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new FieldNotFoundException("User with ID " + username + " not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(optionalUser.get(), UserEntity.class);
    }

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

        UserEntity userAuthenticated = findByUsername(loginUserDto.username());

        session.setAttribute("username", loginUserDto.username());
        session.setAttribute("userId", userAuthenticated.getId());

        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }
}
