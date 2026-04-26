package com.mockeval.controller;

import com.mockeval.entity.EvaluationRound;
import com.mockeval.repository.EvaluationRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/round")
@CrossOrigin
public class RoundController {

    @Autowired
    private EvaluationRoundRepository repo;

    @PostMapping
    public EvaluationRound create(@RequestBody EvaluationRound round) {
        return repo.save(round);
    }

    @GetMapping
    public List<EvaluationRound> getAll() {
        return repo.findAll();
    }
}