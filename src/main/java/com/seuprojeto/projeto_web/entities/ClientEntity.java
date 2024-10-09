package com.seuprojeto.projeto_web.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.seuprojeto.projeto_web.enums.CnhCategory;
import com.seuprojeto.projeto_web.enums.Sexo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Column(nullable = false)
    private LocalDate dtNascimento;

    @Column(unique = true, nullable = false)
    private String cnh;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CnhCategory cnhCategory;

    @Column(nullable = false)
    private LocalDate cnhDtMaturity;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String complement;
        
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean stExcluido = false;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
