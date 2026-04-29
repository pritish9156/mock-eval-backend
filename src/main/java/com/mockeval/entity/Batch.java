package com.mockeval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String startDate;
    private String endDate;
    private Boolean active = true;

    @OneToMany(mappedBy = "batch")
    @JsonIgnore   // 🔥 ADD THIS
    private List<Round> rounds;
}