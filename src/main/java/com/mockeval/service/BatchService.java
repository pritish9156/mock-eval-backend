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
        return repo.save(batch);
    }

    public List<Batch> getAll() {
        return repo.findAll();
    }
}