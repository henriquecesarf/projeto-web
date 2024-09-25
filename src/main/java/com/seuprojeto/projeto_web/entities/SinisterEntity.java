package com.seuprojeto.projeto_web.entities;

import com.seuprojeto.projeto_web.enums.Gravity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sinister")
public class SinisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String identification;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gravity gravity;

    public SinisterEntity() {}

    public SinisterEntity(String identification, Gravity gravity) {
        this.identification = identification;
        this.gravity = gravity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Gravity getGravity() {
        return gravity;
    }
    
    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

}
