package com.seuprojeto.projeto_web.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.seuprojeto.projeto_web.entities.UserEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class ModelUserDetailsImpl implements UserDetails {

    private UserEntity modelUser;

    public ModelUserDetailsImpl(UserEntity modelUser) {
        this.modelUser = modelUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return modelUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return modelUser.getPassword();
    }

    @Override
    public String getUsername() {
        return modelUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
