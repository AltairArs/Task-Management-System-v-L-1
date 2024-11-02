package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserLoginRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("register/")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody @Valid UserRegistrationRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("login/")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody @Valid UserLoginRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("refresh-token/")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody @Valid RefreshJwtTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
