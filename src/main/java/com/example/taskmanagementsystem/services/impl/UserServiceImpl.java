package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.repo.UserRepository;
import com.example.taskmanagementsystem.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getUser(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.getByEmail(email);
        return optionalUserEntity.orElseThrow(()-> new EntityNotFoundException("Cannot find user with this email: " + email));
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.getById(id);
        return optionalUserEntity.orElseThrow(()-> new EntityNotFoundException("Cannot find user with id"));
    }
}
