package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mockeval.entity.Round;
import com.mockeval.service.RoundService;

import java.util.List;

@RestController
@RequestMapping("/round")
@CrossOrigin
public class RoundController {

    @Autowired
    private RoundService service;

    @PostMapping
    public Round create(@RequestBody Round round) {

        // 🔥 BASIC VALIDATION (no crash, clear error)
        if (round.getName() == null || round.getName().isEmpty()) {
            throw new RuntimeException("Round name required");
        }

        if (round.getBatch() == null || round.getBatch().getId() == null) {
            throw new RuntimeException("Batch required");
        }

        if (round.getTechnology() == null || round.getTechnology().getId() == null) {
            throw new RuntimeException("Technology required");
        }

            return service.save(round);
    }

    @GetMapping
    public List<Round> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Round update(@PathVariable Long id, @RequestBody Round round) {
        return service.update(id, round);
    }

    @PutMapping("/deactivate/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }
}