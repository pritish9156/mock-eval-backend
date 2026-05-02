package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Assignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT a FROM Assignment a WHERE a.evaluator.id = :id AND a.active = true")
    List<Assignment> getActiveAssignments(@Param("id") Long id);

    List<Assignment> findByActiveTrue();
    List<Assignment> findByEvaluatorIdAndActiveTrue(Long id);
}