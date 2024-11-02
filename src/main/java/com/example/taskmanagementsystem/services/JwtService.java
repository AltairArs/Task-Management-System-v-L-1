package com.example.taskmanagementsystem.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public abstract String generateAccessToken(UserDetails userDetails);
    public abstract String generateRefreshToken(UserDetails userDetails);
    public abstract String extract(String token, String value);
}
