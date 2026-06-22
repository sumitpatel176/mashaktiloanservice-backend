package com.sts.controller;

import com.sts.dto.LoginRequest;
import com.sts.entity.StaffUser;
import com.sts.repository.StaffUserRepository;
import com.sts.service.PasswordResetService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private StaffUserRepository staffUserRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    // 1. REGISTER ENDPOINT 
    @PostMapping("/register")
    public ResponseEntity<String> registerStaff(@RequestBody StaffUser staffUser) {
        // Check karo ki username pehle se toh nahi hai database mein
        if (staffUserRepository.findByUsername(staffUser.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        String rawPassword = staffUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        staffUser.setPassword(encodedPassword);

        if (staffUser.getRole() == null || staffUser.getRole().isEmpty()) {
            staffUser.setRole("admin"); 
        } else {
            staffUser.setRole(staffUser.getRole().toLowerCase());
        }

       
        staffUserRepository.save(staffUser);
        return ResponseEntity.ok("Admin registered successfully with secure password!");
    }

    // 2. LOGIN ENDPOINT 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Yeh line database me jaakar real user credentials check karegi
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Agar sab sahi raha toh status 200 OK jayega
            return ResponseEntity.ok(Map.of("status", "success", "message", "Welcome Admin!"));
        } catch (Exception e) {
            // Agar galat credentials hue toh 401 Unauthorized jayega
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
    
    @Autowired
    private PasswordResetService passwordResetService;

    // 1. Forgot Password Endpoint 
    @PostMapping("/public/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username) {
        String result = passwordResetService.sendResetToken(username);
        if (result.equals("Success")) {
            return ResponseEntity.ok("Password reset link has been sent to your email.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // 2. Reset Password Endpoint 
    @PostMapping("/public/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String result = passwordResetService.resetPassword(token, newPassword);
        if (result.equals("Password Reset Successful!")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
