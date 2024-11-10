package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.UserRepository;
import com.example.taskmanagementsystem.services.PasswordEncoderService;
import com.example.taskmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    @Override
    public UserEntity getUser(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.getByEmail(email);
        return optionalUserEntity.orElseThrow(()-> new TaskManagementException("Could not found user with this Email: " + email));
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.getById(id);
        return optionalUserEntity.orElseThrow(()-> new TaskManagementException("Could not found user with this id: " + id));
    }

    @Override
    public UserEntity updateUser(UserEntity user, UserUpdateRequest userUpdateRequest) {
        user.setPassword(passwordEncoderService.getPasswordEncoder().encode(userUpdateRequest.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
