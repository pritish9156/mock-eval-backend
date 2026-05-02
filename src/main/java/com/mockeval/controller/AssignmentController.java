package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mockeval.entity.Assignment;
import com.mockeval.service.AssignmentService;

import java.util.List;

@RestController
@RequestMapping("/assignment")
@CrossOrigin
public class AssignmentController {

    @Autowired
    private AssignmentService service;

    @PostMapping
    public Assignment create(@RequestBody Assignment a) {
        return service.save(a);
    }

    @GetMapping
    public List<Assignment> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Assignment update(@PathVariable Long id, @RequestBody Assignment a) {
        return service.update(id, a);
    }

    @PutMapping("/deactivate/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }

    @GetMapping("/evaluator/{id}")
    public List<Assignment> getByEvaluator(@PathVariable Long id) {
        return service.getByEvaluator(id);
    }
}