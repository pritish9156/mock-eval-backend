package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mockeval.entity.Participant;
import com.mockeval.service.ParticipantService;

@RestController
@RequestMapping("/participants")
@CrossOrigin
public class ParticipantController {

    @Autowired
    private ParticipantService service;

    @PostMapping
    public Participant create(@RequestBody Participant participant) {
        return service.save(participant);
    }

    @GetMapping
    public List<Participant> getAll() {
        return service.getAll();
    }
}