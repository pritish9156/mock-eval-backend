package com.mockeval.service;

import com.mockeval.entity.Batch;
import com.mockeval.entity.Technology;
import com.mockeval.repository.BatchRepository;
import com.mockeval.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockeval.entity.Round;
import com.mockeval.repository.RoundRepository;

import java.util.List;

@Service
public class RoundService {

    @Autowired
    private RoundRepository repo;

    @Autowired
    private BatchRepository batchRepo;

    @Autowired
    private TechnologyRepository techRepo;

    public Round save(Round round) {

        if (round.getBatch() == null || round.getBatch().getId() == null) {
            throw new RuntimeException("Batch is missing ❌");
        }

        if (round.getTechnology() == null || round.getTechnology().getId() == null) {
            throw new RuntimeException("Technology is missing ❌");
        }

        Batch batch = batchRepo.findById(round.getBatch().getId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Technology tech = techRepo.findById(round.getTechnology().getId())
                .orElseThrow(() -> new RuntimeException("Technology not found"));

        Round newRound = new Round();
        newRound.setName(round.getName());
        newRound.setRoundNumber(round.getRoundNumber());
        newRound.setBatch(batch);
        newRound.setTechnology(tech);
        newRound.setActive(true);

        return repo.save(newRound);
    }

    public List<Round> getAll() {
        return repo.findByActiveTrue(); // 🔥 only active
    }

    // 🔥 DEACTIVATE
    public void deactivate(Long id) {
        Round r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Round not found"));

        r.setActive(false);
        repo.save(r);
    }

    public Round update(Long id, Round updated) {

        Round existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Round not found"));

        Batch batch = batchRepo.findById(updated.getBatch().getId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Technology tech = techRepo.findById(updated.getTechnology().getId())
                .orElseThrow(() -> new RuntimeException("Technology not found"));

        existing.setName(updated.getName());
        existing.setRoundNumber(updated.getRoundNumber());
        existing.setBatch(batch);           // ✅ FIXED
        existing.setTechnology(tech);       // ✅ FIXED

        return repo.save(existing);
    }
}