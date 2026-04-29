package com.mockeval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private Boolean active = true;

    @OneToMany(mappedBy = "technology")
    @JsonIgnore   // 🔥 ADD THIS
    private List<Round> rounds;
}