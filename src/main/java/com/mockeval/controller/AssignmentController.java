package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mockeval.entity.Assignment;
import com.mockeval.service.AssignmentService;

@RestController
@RequestMapping("/assignments")
@CrossOrigin
public class AssignmentController {

    @Autowired
    private AssignmentService service;

    @PostMapping
    public Assignment create(@RequestBody Assignment assignment) {
        return service.save(assignment);
    }

    @GetMapping
    public List<Assignment> getAll() {
        return service.getAll();
    }
}