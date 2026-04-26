package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.mockeval.entity.*;
import com.mockeval.repository.*;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository repo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    public Evaluation save(Evaluation evaluation) {

        Assignment assignment = assignmentRepo.findById(
                evaluation.getAssignment().getId()
        ).orElseThrow(() -> new RuntimeException("Assignment not found"));

        evaluation.setAssignment(assignment);

        return repo.save(evaluation);
    }

    public List<Evaluation> getAll() {
        return repo.findAll();
    }
}