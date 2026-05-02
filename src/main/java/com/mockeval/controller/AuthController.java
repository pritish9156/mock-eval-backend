package com.mockeval.controller;

import com.mockeval.entity.PasswordResetToken;
import com.mockeval.repository.PasswordResetTokenRepo;
import com.mockeval.repository.UserRepository;
import com.mockeval.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mockeval.dto.LoginRequest;
import com.mockeval.entity.User;
import com.mockeval.security.JwtUtil;
import com.mockeval.service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordResetTokenRepo tokenRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {

        User user = userService.login(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());   // 🔥 ADD
        response.put("role", user.getRole()); // 🔥 ADD
        response.put("email", user.getEmail());

        return response;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");

        User user = userRepo.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // 🔥 Generate Token
        String token = UUID.randomUUID().toString();

        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setUser(user);
        prt.setUsed(false);
        prt.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        tokenRepo.save(prt);

        // 🔥 Create Reset Link
        String link = "http://localhost:3000/reset-password?token=" + token;

        // 🔥 Send Email
        emailService.sendEmail(user.getEmail(), "Reset Password", link);

        return ResponseEntity.ok("Reset link sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {

        String tokenValue = request.get("token");
        String newPassword = request.get("newPassword");

        PasswordResetToken token = tokenRepo.findByToken(tokenValue);

        if (token == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        if (token.isUsed()) {
            return ResponseEntity.badRequest().body("Token already used");
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired");
        }

        // 🔥 Update Password
        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // 🔥 Mark Token Used
        token.setUsed(true);
        tokenRepo.save(token);

        return ResponseEntity.ok("Password reset successful");
    }
}