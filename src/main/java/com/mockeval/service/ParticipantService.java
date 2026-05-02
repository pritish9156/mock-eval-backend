package com.mockeval.service;

import com.mockeval.entity.Batch;
import com.mockeval.entity.Technology;
import com.mockeval.repository.BatchRepository;
import com.mockeval.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mockeval.entity.Participant;
import com.mockeval.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repo;

    @Autowired
    private BatchRepository batchRepo;

    @Autowired
    private TechnologyRepository techRepo;

    public Participant save(Participant p) {

        Participant existing = repo.findByEmail(p.getEmail());

        if (existing != null) {

            if (!existing.getActive()) {
                // 🔥 REACTIVATE
                existing.setActive(true);
                existing.setName(p.getName());
                existing.setBatch(p.getBatch());
                existing.setTechnology(p.getTechnology());

                return repo.save(existing);
            }

            throw new RuntimeException("Participant already exists");
        }

        p.setActive(true);
        return repo.save(p);
    }

    public List<Participant> getAll() {
        return repo.findByActiveTrue();
    }

    public Participant update(Long id, Participant updated) {

        Participant existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());

        // 🔥 FIX: Fetch real Batch
        if (updated.getBatch() != null && updated.getBatch().getId() != null) {
            Batch batch = batchRepo.findById(updated.getBatch().getId())
                    .orElseThrow(() -> new RuntimeException("Batch not found"));

            existing.setBatch(batch);
        }

        // 🔥 FIX: Fetch real Technology
        if (updated.getTechnology() != null && updated.getTechnology().getId() != null) {
            Technology tech = techRepo.findById(updated.getTechnology().getId())
                    .orElseThrow(() -> new RuntimeException("Technology not found"));

            existing.setTechnology(tech);
        }

        return repo.save(existing);
    }

    public void deactivate(Long id) {

        Participant p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        p.setActive(false); // 🔥 soft delete
        repo.save(p);
    }

}