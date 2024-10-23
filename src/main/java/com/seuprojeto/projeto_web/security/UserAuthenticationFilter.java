package com.seuprojeto.projeto_web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.seuprojeto.projeto_web.entities.UserEntity;
import com.seuprojeto.projeto_web.repositories.UserRepository;
import com.seuprojeto.projeto_web.security.jwt.JwtTokenService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (verificaEndpointsPublicos(request)) {
            String token = recuperaToken(request);
            if (token != null) {
                String subject = jwtTokenService.pegarToken(token);
                UserEntity modelUser = userRepository.findByUsername(subject).get();
                ModelUserDetailsImpl modelUserDetails = new ModelUserDetailsImpl(modelUser);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                modelUserDetails.getUsername(),
                                null,
                                modelUserDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("Token inexistente!");
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean verificaEndpointsPublicos(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    
    // Lista de endpoints públicos
    List<String> endpointsPublicos = Arrays.asList(
            "/api/users/login", 
            "/api/users"
    );

    // Verifica se a URI corresponde a algum dos endpoints públicos
    for (String endpoint : endpointsPublicos) {
        if (requestURI.startsWith(endpoint)) {
            return false; // O endpoint é público, então não precisa de autenticação
        }
    }
    
    // Se a URI não corresponder a nenhum dos públicos, retorna true, indicando que é privado
    return true; 
}

    private String recuperaToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}
