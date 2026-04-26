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

    public User save(User user) {
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }
}