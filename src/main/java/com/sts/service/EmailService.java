package com.sts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String tousername, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(tousername);
        message.setSubject("Password Reset Request - MaShakti Loan Service");
        message.setText("Hello,\n\n"
                + "You requested to reset your password. Please click the link below to set a new password:\n"
                + resetLink + "\n\n"
                + "Note: This link is valid for only 15 minutes.\n\n"
                + "If you did not request this, please ignore this email.");
        
        mailSender.send(message);
    }
}