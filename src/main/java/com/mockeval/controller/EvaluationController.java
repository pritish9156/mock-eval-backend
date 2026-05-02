package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mockeval.entity.Evaluation;
import com.mockeval.service.EvaluationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
@CrossOrigin
public class EvaluationController {

    @Autowired
    private EvaluationService service;

    // CREATE
    @PostMapping
    public Evaluation create(@RequestBody Evaluation eval) {

        if (eval.getStrengths() == null)
            eval.setStrengths(new ArrayList<>());

        if (eval.getWeaknesses() == null)
            eval.setWeaknesses(new ArrayList<>());

        if (eval.getPlan() == null)
            eval.setPlan(new ArrayList<>());

        return service.save(eval);
    }

    // ADMIN VIEW
    @GetMapping
    public List<Evaluation> getAll() {
        return service.getAll();
    }

    // EVALUATOR VIEW
    @GetMapping("/evaluator/{id}")
    public List<Evaluation> getByEvaluator(@PathVariable Long id) {
        return service.getByEvaluator(id);
    }

    @GetMapping("/report")
    public List<Evaluation> getReport(
            @RequestParam(required = false) Long batchId,
            @RequestParam(required = false) Long techId
    ) {
        return service.getReport(batchId, techId);
    }
}