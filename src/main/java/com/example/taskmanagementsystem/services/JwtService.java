package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public abstract String generateAccessToken(UserDetails userDetails);
    public abstract String generateRefreshToken(UserDetails userDetails);
    public abstract String extract(String token, String value);
    public abstract JwtAuthenticationResponse generateTokenResponse(UserEntity user);
}
