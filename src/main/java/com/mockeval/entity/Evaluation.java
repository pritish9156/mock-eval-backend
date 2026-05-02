package com.mockeval.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private Boolean evaluated;

    @Column(length = 2000)
    private String feedback;

    @ElementCollection
    private List<String> strengths = new ArrayList<>();

    @ElementCollection
    private List<String> weaknesses = new ArrayList<>();

    @ElementCollection
    private List<String> plan = new ArrayList<>();

    private LocalDateTime evaluationTime;

    private Boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}