package com.mockeval.service;

import com.mockeval.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mockeval.entity.User;
import com.mockeval.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserRepository userRepo;

    public User save(User user) {

        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new CustomException("Email already exists");
        }

        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User login(String email, String password) {

        User user = userRepo.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            throw new CustomException("Invalid email or password");
        }

        return user;
    }
}