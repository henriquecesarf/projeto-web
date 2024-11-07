package com.seuprojeto.projeto_web.entities;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "fine_1_to_4_days", nullable = false)
    private Double fine1To4Days;

    @Column(name = "fine_5_to_9_days", nullable = false)
    private Double fine5To9Days;

    @Column(name = "fine_10_Days_or_more", nullable = false)
    private Double fine10DaysOrMore;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
