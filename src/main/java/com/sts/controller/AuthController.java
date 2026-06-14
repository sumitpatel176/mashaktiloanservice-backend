package com.sts.controller;

import com.sts.dto.LoginRequest;
import com.sts.entity.StaffUser;
import com.sts.repository.StaffUserRepository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "https://mashaktiloanservice-frontend-gray.vercel.app"})
public class AuthController {

    @Autowired
    private StaffUserRepository staffUserRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    // 1. REGISTER ENDPOINT (Naya Account Banane Ke Liye)
    @PostMapping("/register")
    public ResponseEntity<String> registerStaff(@RequestBody StaffUser staffUser) {
        // Check karo ki username pehle se toh nahi hai database mein
        if (staffUserRepository.findByUsername(staffUser.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        // Plain password ko BCrypt se encrypt (hash) karo
        String rawPassword = staffUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        staffUser.setPassword(encodedPassword);

        // Bhaiya akele hain, isliye default role 'admin' hi set rahega
        if (staffUser.getRole() == null || staffUser.getRole().isEmpty()) {
            staffUser.setRole("admin"); 
        } else {
            staffUser.setRole(staffUser.getRole().toLowerCase());
        }

        // Database mein save karo
        staffUserRepository.save(staffUser);
        return ResponseEntity.ok("Admin registered successfully with secure password!");
    }

    // 2. LOGIN ENDPOINT (Frontend Form Se Login Check Karne Ke Liye)
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
}
