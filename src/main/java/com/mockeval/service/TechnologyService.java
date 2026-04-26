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

        if (repo.findAll().stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(tech.getName()))) {
            throw new CustomException("Technology already exists");
        }

        return repo.save(tech);
    }

    public List<Technology> getAll() {
        return repo.findAll();
    }
}