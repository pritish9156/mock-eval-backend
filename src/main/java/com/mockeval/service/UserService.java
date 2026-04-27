package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.mockeval.entity.User;
import com.mockeval.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public User login(String email, String password) {

        User user = repo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User is deactivated");
        }

        return user;
    }

    // CREATE
    public User save(User user) {
        return repo.save(user);
    }

    // READ ONLY ACTIVE USERS
    public List<User> getAll() {
        return repo.findByActiveTrue(); // ✅ IMPORTANT FIX
    }

    // DEACTIVATE USER (SOFT DELETE)
    public void deactivate(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        repo.save(user);
    }

    // UPDATE
    public User update(Long id, User user) {
        user.setId(id);
        return repo.save(user);
    }

    // LOGIN SUPPORT
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }
}