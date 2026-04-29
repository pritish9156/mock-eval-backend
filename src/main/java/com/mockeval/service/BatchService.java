package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mockeval.entity.Batch;
import com.mockeval.repository.BatchRepository;

@Service
public class BatchService {

    @Autowired
    private BatchRepository repo;

    public Batch save(Batch batch) {
        batch.setActive(true);
        return repo.save(batch);
    }

    public List<Batch> getAll() {
        return repo.findByActiveTrue();
    }

    public void deactivate(Long id) {
        Batch b = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        b.setActive(false);
        repo.save(b);
    }

    public Batch update(Long id, Batch updated) {
        Batch existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        existing.setName(updated.getName());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());

        return repo.save(existing);
    }
}