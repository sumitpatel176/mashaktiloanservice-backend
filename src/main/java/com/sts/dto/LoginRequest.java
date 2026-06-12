package com.sts.dto; // 👈 Aapke project ke package ke hisab se ise check kar lena

public class LoginRequest {
    
    private String username;
    private String password;

    // Default Constructor (Jackson library ke liye zaroori hai)
    public LoginRequest() {
    }

    // Parameterized Constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 🔥 Getters and Setters (Inke bina Controller data nahi nikal payega)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}