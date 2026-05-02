package com.mockeval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "start_date"})
)
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String startDate;
    private String endDate;
    private Boolean active = true;

    @JsonIgnoreProperties({"batch", "technology"})  // 🔥 ADD
    @OneToMany(mappedBy = "batch")
    @JsonIgnore   // 🔥 ADD THIS
    private List<Round> rounds;

}