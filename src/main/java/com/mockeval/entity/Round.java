package com.mockeval.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "round_number")
    private Integer roundNumber;

    @JsonProperty(defaultValue = "true")
    private Boolean active = true; // 🔥 NEW

    @ManyToOne
    @JoinColumn(name = "batch_id")
    @JsonIgnoreProperties({"rounds"}) // 🔥 FIX
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "technology_id")
    @JsonIgnoreProperties({"rounds"}) // 🔥 FIX
    private Technology technology;
}