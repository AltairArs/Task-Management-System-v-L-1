package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserLoginRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.domain.dto.responses.ErrorResponse;
import com.example.taskmanagementsystem.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorResponse.fromBindingResult(bindingResult));
        } else {
            return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
        }
    }

    @PostMapping("login/")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorResponse.fromBindingResult(bindingResult));
        } else {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }
    }

    @PostMapping("refresh-token/")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshJwtTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
