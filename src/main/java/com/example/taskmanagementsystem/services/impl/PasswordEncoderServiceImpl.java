package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.services.PasswordEncoderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {
    @Override
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
