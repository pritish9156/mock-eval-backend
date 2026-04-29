package com.mockeval.service;

import com.mockeval.exception.CustomException;
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
    private RoundRepository roundRepo;

    public Assignment save(Assignment assignment) {

        Participant participant = participantRepo.findById(
                assignment.getParticipant().getId()
        ).orElseThrow(() -> new CustomException("Participant not found"));

        User evaluator = userRepo.findById(
                assignment.getEvaluator().getId()
        ).orElseThrow(() -> new CustomException("Evaluator not found"));

        Technology technology = techRepo.findById(
                assignment.getTechnology().getId()
        ).orElseThrow(() -> new CustomException("Technology not found"));

        // 🔥 FIXED HERE
        Round round = roundRepo.findById(
                assignment.getRound().getId()
        ).orElseThrow(() -> new CustomException("Round not found"));

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