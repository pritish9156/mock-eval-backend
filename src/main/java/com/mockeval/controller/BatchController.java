package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mockeval.entity.Batch;
import com.mockeval.service.BatchService;

@RestController
@RequestMapping("/batch")
@CrossOrigin
public class BatchController {

    @Autowired
    private BatchService service;

    @PostMapping
    public Batch create(@RequestBody Batch batch) {
        return service.save(batch);
    }

    @GetMapping
    public List<Batch> getAll() {
        return service.getAll();
    }
}