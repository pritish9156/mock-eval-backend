package com.mockeval.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluator_id")
    private User evaluator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "round_id")
    private Round round;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technology_id")
    private Technology technology;
}