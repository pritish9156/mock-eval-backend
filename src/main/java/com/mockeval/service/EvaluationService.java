package com.mockeval.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockeval.entity.*;
import com.mockeval.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository repo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    // 🔥 SAVE EVALUATION
    @Transactional
    public Evaluation save(Evaluation e) {

        Assignment assignment = assignmentRepo.findById(
                e.getAssignment().getId()
        ).orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setActive(false);
        assignmentRepo.saveAndFlush(assignment);

        Evaluation newE = new Evaluation();

        newE.setScore(e.getScore());
        newE.setFeedback(e.getFeedback());

        newE.setStrengths(e.getStrengths());
        newE.setWeaknesses(e.getWeaknesses());
        newE.setPlan(e.getPlan());

        newE.setAssignment(assignment);
        newE.setEvaluationTime(LocalDateTime.now());
        newE.setActive(true);
        newE.setEvaluated(true);

        return repo.save(newE);
    }

    // 🔥 GET FOR EVALUATOR
    public List<Evaluation> getByEvaluator(Long evaluatorId) {
        return repo.findByAssignmentEvaluatorId(evaluatorId);
    }

    public List<Evaluation> getAll() {
        return repo.findByActiveTrue();
    }

    public List<Evaluation> getReport(Long batchId, Long techId) {

        List<Evaluation> all = repo.findAll();

        return all.stream()
                .filter(e -> batchId == null ||
                        (e.getAssignment() != null &&
                                e.getAssignment().getParticipant() != null &&
                                e.getAssignment().getParticipant().getBatch() != null &&
                                e.getAssignment().getParticipant().getBatch().getId().equals(batchId))
                )
                .filter(e -> techId == null ||
                        (e.getAssignment() != null &&
                                e.getAssignment().getTechnology() != null &&
                                e.getAssignment().getTechnology().getId().equals(techId))
                )
                .toList();
    }
}