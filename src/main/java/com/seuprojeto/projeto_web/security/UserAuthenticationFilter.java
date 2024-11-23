package com.seuprojeto.projeto_web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        // Recupera o token da requisição
        String token = recoveryToken(request);
        
        if (token != null) {
            try {
                String usernameAuthenticated = (String) session.getAttribute("username");
                
                // Obtém o nome de usuário a partir do token
                String usernameToken = jwtTokenService.getToken(token);
                
                if (!usernameAuthenticated.equals(usernameToken)) {
                    // Se o token não pertence ao usuário autenticado, rejeita a requisição
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token não pertence ao usuário autenticado!");
                    return;
                }else{
                    String subject = jwtTokenService.getToken(token);
                    UserEntity modelUser = userRepository.findByUsername(subject).get();
                    ModelUserDetailsImpl modelUserDetails = new ModelUserDetailsImpl(modelUser);
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(
                                    modelUserDetails.getUsername(),
                                    null,
                                    modelUserDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalido ou expirado!");
                return;
            }
        }

        // Passa a requisição para o próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
