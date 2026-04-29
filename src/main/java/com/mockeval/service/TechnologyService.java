package com.mockeval.service;

import com.mockeval.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mockeval.entity.Technology;
import com.mockeval.repository.TechnologyRepository;

@Service
public class TechnologyService {

    @Autowired
    private TechnologyRepository repo;

    public Technology save(Technology tech) {

        Technology existing = repo.findByName(tech.getName());

        if (existing != null) {

            if (!existing.getActive()) {
                existing.setActive(true); // 🔥 REACTIVATE
                return repo.save(existing);
            }

            throw new RuntimeException("Technology already exists ❌");
        }

        tech.setActive(true);
        return repo.save(tech);
    }

    public List<Technology> getAll() {
        return repo.findByActiveTrue();
    }

    public void deactivate(Long id) {
        Technology t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found"));

        t.setActive(false);
        repo.save(t);
    }

    public Technology update(Long id, Technology updated) {
        Technology existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found"));

        existing.setName(updated.getName());

        return repo.save(existing);
    }
}