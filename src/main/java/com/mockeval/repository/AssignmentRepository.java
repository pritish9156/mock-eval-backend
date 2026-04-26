package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}