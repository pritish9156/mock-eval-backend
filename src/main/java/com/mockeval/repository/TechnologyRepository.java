package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}