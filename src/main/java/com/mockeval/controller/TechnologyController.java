package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mockeval.entity.Technology;
import com.mockeval.service.TechnologyService;

@RestController
@RequestMapping("/technology")
@CrossOrigin
public class TechnologyController {

    @Autowired
    private TechnologyService service;

    @PostMapping
    public Technology create(@RequestBody Technology tech) {
        return service.save(tech);
    }

    @GetMapping
    public List<Technology> getAll() {
        return service.getAll();
    }
}