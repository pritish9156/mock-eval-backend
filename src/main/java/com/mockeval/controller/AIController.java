package com.mockeval.controller;

import com.mockeval.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AIController {

    @Autowired
    private AIService service;

    @PostMapping("/improve")
    public Map<String, Object> improve(@RequestBody Map<String, String> req) {
        return service.improve(req.get("text"));
    }
}