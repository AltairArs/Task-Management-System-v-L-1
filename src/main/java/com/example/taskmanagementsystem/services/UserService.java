package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;

public interface UserService {
    public abstract UserEntity getUser(String email);
}
