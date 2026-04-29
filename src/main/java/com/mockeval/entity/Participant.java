package com.mockeval.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    // 🔥 RELATION WITH BATCH
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    // 🔥 RELATION WITH TECHNOLOGY
    @ManyToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;
}