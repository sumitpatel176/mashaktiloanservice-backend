package com.sts.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sts.entity.PasswordResetToken;
import com.sts.entity.StaffUser;
import com.sts.repository.PasswordResetTokenRepository;
import com.sts.repository.StaffUserRepository;

@Service
public class PasswordResetService {

    @Autowired
    private StaffUserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Generate token and send email
    public String sendResetToken(String username) {
        Optional<StaffUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "Email not found!";
        }

        StaffUser user = userOpt.get();

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        tokenRepository.save(resetToken);

        // React App Reset Password Route
        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        emailService.sendResetPasswordEmail(user.getUsername(), resetLink);
        return "Success";
    }

    // 2. Process password reset
    public String resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return "Invalid Token!";
        }

        PasswordResetToken resetToken = tokenOpt.get();

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            return "Token Expired!";
        }

        StaffUser user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken); // Delete token after use
        return "Password Reset Successful!";
    }
}