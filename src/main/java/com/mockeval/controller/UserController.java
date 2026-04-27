package com.mockeval.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.mockeval.entity.User;
import com.mockeval.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    // CREATE
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return service.save(user);
    }

    // READ (ONLY ACTIVE USERS)
    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getAll();
    }

    // SOFT DELETE (DEACTIVATE)
    @PutMapping("/users/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id) {
        service.deactivate(id);
        return "User deactivated successfully";
    }

    // UPDATE
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }
}