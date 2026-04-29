package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Technology;

import java.util.List;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    List<Technology> findByActiveTrue();
    Technology findByName(String name);
}