package com.example.taskmanagementsystem.services;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderService {
    public abstract PasswordEncoder getPasswordEncoder();
}
