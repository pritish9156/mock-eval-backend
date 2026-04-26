package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.mockeval.entity.*;
import com.mockeval.repository.*;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository repo;

    @Autowired
    private ParticipantRepository participantRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TechnologyRepository techRepo;

    @Autowired
    private EvaluationRoundRepository roundRepo;

    public Assignment save(Assignment assignment) {

        // Fetch full Participant
        Participant participant = participantRepo.findById(
                assignment.getParticipant().getId()
        ).orElseThrow(() -> new RuntimeException("Participant not found"));

        // Fetch full Evaluator
        User evaluator = userRepo.findById(
                assignment.getEvaluator().getId()
        ).orElseThrow(() -> new RuntimeException("Evaluator not found"));

        // Fetch full Technology
        Technology technology = techRepo.findById(
                assignment.getTechnology().getId()
        ).orElseThrow(() -> new RuntimeException("Technology not found"));

        // Fetch full Round
        EvaluationRound round = roundRepo.findById(
                assignment.getRound().getId()
        ).orElseThrow(() -> new RuntimeException("Round not found"));

        // Set full objects
        assignment.setParticipant(participant);
        assignment.setEvaluator(evaluator);
        assignment.setTechnology(technology);
        assignment.setRound(round);

        return repo.save(assignment);
    }

    public List<Assignment> getAll() {
        return repo.findAll();
    }
}