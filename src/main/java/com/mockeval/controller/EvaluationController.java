package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.mockeval.entity.Evaluation;
import com.mockeval.service.EvaluationService;

@RestController
@RequestMapping("/evaluations")
@CrossOrigin
public class EvaluationController {

    @Autowired
    private EvaluationService service;

    @PostMapping
    public Evaluation create(@RequestBody Evaluation evaluation) {
        return service.save(evaluation);
    }

    @GetMapping
    public List<Evaluation> getAll() {
        return service.getAll();
    }
}