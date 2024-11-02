package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserLoginRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;

public interface AuthenticationService {
    public abstract JwtAuthenticationResponse register(UserRegistrationRequest request);
    public abstract JwtAuthenticationResponse authenticate(UserLoginRequest request);
    public abstract JwtAuthenticationResponse refreshToken(RefreshJwtTokenRequest request);
}
