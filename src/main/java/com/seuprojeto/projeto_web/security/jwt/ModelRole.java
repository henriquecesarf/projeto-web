package com.seuprojeto.projeto_web.security.jwt;

import jakarta.persistence.*;
import lombok.*;
import com.seuprojeto.projeto_web.enums.Role;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="roles")
public class ModelRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role name;

}
