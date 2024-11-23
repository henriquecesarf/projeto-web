package com.seuprojeto.projeto_web.entities;

import jakarta.persistence.*;
import lombok.*;
import com.seuprojeto.projeto_web.enums.Role;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role name;

}
