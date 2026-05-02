package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import com.mockeval.entity.User;
import com.mockeval.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(String email, String password) {

        User user = repo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User is deactivated");
        }

        return user;
    }

    // CREATE
    public User save(User user) {

        User existing = repo.findByEmail(user.getEmail());

        if (existing != null) {

            if (!existing.isActive()) {
                existing.setActive(true);   // 🔥 REACTIVATE
                existing.setName(user.getName()); // optional update
                return repo.save(existing);
            }

            throw new RuntimeException("Email already exists ❌");
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // if using encoding
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
    public User updateUser(Long id, User updatedUser) {

        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Always update
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());

        // 🔥 FIX: Only update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return repo.save(existing);
    }

    // LOGIN SUPPORT
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }
}