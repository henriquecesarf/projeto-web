package com.seuprojeto.projeto_web.entities;

import java.util.ArrayList;
import java.util.List;

import com.seuprojeto.projeto_web.enums.Gravity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "sinister")
public class SinisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String identification;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gravity gravity;

    @OneToMany(mappedBy = "sinister")
    private List<RentalSinister> rentalSinisters = new ArrayList<>();
}
