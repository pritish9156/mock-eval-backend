package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Evaluation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT e FROM Evaluation e JOIN e.assignment a WHERE a.technology.id = :techId")
    List<Evaluation> findByTechnology(Long techId);

    List<Evaluation> findByActiveTrue();

    List<Evaluation> findByAssignmentEvaluatorId(Long evaluatorId); // 🔥 important

    @Query("""
SELECT e FROM Evaluation e
WHERE (:batchId IS NULL OR e.assignment.participant.batch.id = :batchId)
AND (:techId IS NULL OR e.assignment.technology.id = :techId)
""")
    List<Evaluation> getReportData(Long batchId, Long techId);
}