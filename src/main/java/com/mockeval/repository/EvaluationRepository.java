package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}