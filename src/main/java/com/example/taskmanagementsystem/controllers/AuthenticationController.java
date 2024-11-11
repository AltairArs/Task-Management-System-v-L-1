package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserLoginRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts/")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Аутентифицирует пользователей")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователей и выдача токенов")
    @PostMapping("register/")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Аутентификация/вход пользователей и выдача токенов")
    @PostMapping("login/")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Operation(summary = "Обновление JWT-токенов")
    @PostMapping("refresh-token/")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshJwtTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
