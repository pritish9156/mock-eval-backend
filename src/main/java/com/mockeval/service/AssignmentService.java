package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockeval.entity.*;
import com.mockeval.repository.*;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired private AssignmentRepository repo;
    @Autowired private ParticipantRepository participantRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private TechnologyRepository techRepo;
    @Autowired private RoundRepository roundRepo;

    // 🔥 CREATE ASSIGNMENT
    public Assignment save(Assignment assignment) {

        Participant participant = participantRepo.findById(
                assignment.getParticipant().getId()
        ).orElseThrow(() -> new RuntimeException("Participant not found"));

        User evaluator = userRepo.findById(
                assignment.getEvaluator().getId()
        ).orElseThrow(() -> new RuntimeException("Evaluator not found"));

        Technology tech = techRepo.findById(
                assignment.getTechnology().getId()
        ).orElseThrow(() -> new RuntimeException("Technology not found"));

        Round round = roundRepo.findById(
                assignment.getRound().getId()
        ).orElseThrow(() -> new RuntimeException("Round not found"));

        Assignment newA = new Assignment();
        newA.setParticipant(participant);
        newA.setEvaluator(evaluator);
        newA.setTechnology(tech);
        newA.setRound(round);
        newA.setActive(true);

        return repo.save(newA);
    }

    public List<Assignment> getAll() {
        return repo.findByActiveTrue();
    }

    public List<Assignment> getByEvaluator(Long id) {
        return repo.findByEvaluatorIdAndActiveTrue(id);
    }

    public Assignment update(Long id, Assignment updated) {

        Assignment existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        // 🔥 fetch real entities (IMPORTANT)
        Participant p = participantRepo.findById(updated.getParticipant().getId())
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        User e = userRepo.findById(updated.getEvaluator().getId())
                .orElseThrow(() -> new RuntimeException("Evaluator not found"));

        Round r = roundRepo.findById(updated.getRound().getId())
                .orElseThrow(() -> new RuntimeException("Round not found"));

        Technology t = techRepo.findById(updated.getTechnology().getId())
                .orElseThrow(() -> new RuntimeException("Technology not found"));

        existing.setParticipant(p);
        existing.setEvaluator(e);
        existing.setRound(r);
        existing.setTechnology(t);

        return repo.save(existing);
    }

    public void deactivate(Long id) {
        Assignment a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        a.setActive(false);
        repo.save(a);
    }
}