package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BaseController {
    private final UserRepository userRepository;
    @GetMapping("/")
    public List<UserEntity> base(){
        return userRepository.findAll();
    }
}
