package com.mockeval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mockeval.dto.LoginRequest;
import com.mockeval.entity.User;
import com.mockeval.security.JwtUtil;
import com.mockeval.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        try {
            User user = userService.login(request.getEmail(), request.getPassword());

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return response;

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 VERY IMPORTANT
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}