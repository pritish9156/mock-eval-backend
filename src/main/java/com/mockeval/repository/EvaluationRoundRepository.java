package com.mockeval.repository;

import com.mockeval.entity.EvaluationRound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRoundRepository extends JpaRepository<EvaluationRound, Long> {
}