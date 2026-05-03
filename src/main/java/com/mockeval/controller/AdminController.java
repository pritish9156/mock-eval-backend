package com.mockeval.controller;

import com.mockeval.entity.*;
import com.mockeval.repository.*;
import com.mockeval.security.JwtUtil;
import com.mockeval.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired private UserRepository userRepo;
    @Autowired private AdminOtpRepo otpRepo;
    @Autowired private AdminApprovalTokenRepo approvalRepo;
    @Autowired private PasswordResetTokenRepo tokenRepo;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Value("${super.admin.email}")
    private String superAdminEmail;

    // 🔐 SAFE ROLE CHECK
    private boolean isAdmin(User user) {
        return user != null &&
                ("ADMIN".equals(user.getRole()) || "SUPER_ADMIN".equals(user.getRole()));
    }

    // =========================
    // 🔥 SEND OTP
    // =========================
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(
            @RequestBody Map<String, String> req,
            @RequestHeader("Authorization") String authHeader
    ) {

        User requester = userRepo.findByEmail(jwtUtil.extractEmail(authHeader.substring(7)));

        if (!isAdmin(requester)) {
            return ResponseEntity.status(403).body(Map.of("message", "Access Denied"));
        }

        String email = req.get("email").toLowerCase();

        if (userRepo.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User already exists"));
        }

        // 🔥 DELETE OLD OTPs
        otpRepo.deleteByEmail(email);

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        AdminOtp entity = new AdminOtp();
        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setUsed(false);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(entity);

        emailService.sendOtpEmail(superAdminEmail, otp);

        return ResponseEntity.ok(Map.of("message", "OTP sent to Super Admin"));
    }

    // =========================
    // 🔥 CREATE ADMIN VIA OTP
    // =========================
    @PostMapping("/create-via-otp")
    public ResponseEntity<?> createViaOtp(
            @RequestBody Map<String, String> req,
            @RequestHeader("Authorization") String auth
    ) {

        User requester = userRepo.findByEmail(jwtUtil.extractEmail(auth.substring(7)));

        if (!isAdmin(requester)) {
            return ResponseEntity.status(403).body(Map.of("message", "Access Denied"));
        }

        String email = req.get("email").toLowerCase();
        String otp = req.get("otp");

        AdminOtp record = otpRepo.findByEmailAndOtp(email, otp);

        if (record == null || record.isUsed() ||
                record.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid OTP"));
        }

        // 🔥 CREATE USER (NO PASSWORD YET)
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("dummy"));
        user.setRole("ADMIN");
        user.setName(email.split("@")[0]);

        userRepo.save(user);

        // 🔥 GENERATE RESET TOKEN
        String resetToken = UUID.randomUUID().toString();

        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(resetToken);
        prt.setUser(user);
        prt.setUsed(false);
        prt.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        tokenRepo.save(prt);

        // 🔥 SEND RESET LINK
        String link = "http://localhost:3000/reset-password?token=" + resetToken;
        emailService.sendEmail(user.getEmail(), "Set Your Password", link);

        record.setUsed(true);
        otpRepo.save(record);

        return ResponseEntity.ok(Map.of("message", "Admin created. Reset link sent."));
    }

    // =========================
    // 🔥 SEND APPROVAL LINK
    // =========================
    @PostMapping("/send-approval-link")
    public ResponseEntity<?> sendApprovalLink(
            @RequestBody Map<String, String> req,
            @RequestHeader("Authorization") String authHeader
    ) {

        User requester = userRepo.findByEmail(jwtUtil.extractEmail(authHeader.substring(7)));

        if (!isAdmin(requester)) {
            return ResponseEntity.status(403).body(Map.of("message", "Access Denied"));
        }

        String email = req.get("email").toLowerCase();

        if (userRepo.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User already exists"));
        }

        String token = UUID.randomUUID().toString();

        AdminApprovalToken t = new AdminApprovalToken();
        t.setEmail(email);
        t.setToken(token);
        t.setUsed(false);
        t.setExpiryTime(LocalDateTime.now().plusMinutes(10));

        approvalRepo.save(t);

        String link = "http://localhost:8080/admin/approve?token=" + token;

        emailService.sendApprovalEmail(superAdminEmail, link);

        return ResponseEntity.ok(Map.of("message", "Approval link sent"));
    }

    // =========================
    // 🔥 APPROVE LINK
    // =========================
    @GetMapping("/approve")
    public ResponseEntity<?> approve(@RequestParam String token) {

        AdminApprovalToken t = approvalRepo.findByToken(token);

        if (t == null || t.isUsed() ||
                t.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid or expired link");
        }

        String email = t.getEmail().toLowerCase();

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("dummy"));
        user.setRole("ADMIN");
        user.setName(email.split("@")[0]);

        userRepo.save(user);

        // 🔥 RESET TOKEN
        String resetToken = UUID.randomUUID().toString();

        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(resetToken);
        prt.setUser(user);
        prt.setUsed(false);
        prt.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        tokenRepo.save(prt);

        // 🔥 SEND RESET LINK
        String link = "http://localhost:3000/reset-password?token=" + resetToken;
        emailService.sendEmail(user.getEmail(), "Set Your Password", link);

        t.setUsed(true);
        approvalRepo.save(t);

        return ResponseEntity.ok("Admin approved and reset link sent");
    }

    // =========================
    // 🔥 CHECK STATUS (FOR POLLING)
    // =========================
    @GetMapping("/status")
    public ResponseEntity<?> checkStatus(@RequestParam String email) {

        User user = userRepo.findByEmail(email.toLowerCase());

        if (user != null && "ADMIN".equals(user.getRole())) {
            return ResponseEntity.ok(Map.of("approved", true));
        }

        return ResponseEntity.ok(Map.of("approved", false));
    }
}