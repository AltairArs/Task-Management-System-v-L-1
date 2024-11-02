package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.repo.UserRepository;
import com.example.taskmanagementsystem.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity getUser(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.getByEmail(email);
        return optionalUserEntity.orElseThrow(()-> new EntityNotFoundException("Cannot find user with this email: " + email));
    }
}
