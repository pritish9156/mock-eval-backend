package com.mockeval.controller;

import com.mockeval.util.CsvUtil;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/technology/{id}")
    public List<Evaluation> getByTechnology(@PathVariable Long id) {
        return service.getByTechnology(id);
    }

    @GetMapping("/average")
    public Double getAverage() {
        return service.getAverageScore();
    }

    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=report.csv");

        List<Evaluation> list = service.getAll();

        CsvUtil.writeToCsv(response.getWriter(), list);
    }
}