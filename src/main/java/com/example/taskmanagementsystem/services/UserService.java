package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;

import java.util.List;

public interface UserService {
    public abstract UserEntity getUser(String email);
    public abstract List<UserEntity> getAllUsers();
    public abstract UserEntity getUserById(long id);
}
