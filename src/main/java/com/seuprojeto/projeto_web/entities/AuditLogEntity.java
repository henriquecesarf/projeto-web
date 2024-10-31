package com.seuprojeto.projeto_web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit")
public class AuditLogEntity {

    public AuditLogEntity() {
    }

    public AuditLogEntity(Long id, String username, String action, String entity, String details) {
        this.id = id;
        this.username = username;
        this.action = action;
        this.entity = entity;
        this.details = details;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String entity;

    @Column(nullable = true, length = 1024)
    private String details;

    @Column(nullable = false, length = 1024)
    private String currentValues;

    @Column(nullable = false, length = 1024)
    private String previousValues;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
    }

}
