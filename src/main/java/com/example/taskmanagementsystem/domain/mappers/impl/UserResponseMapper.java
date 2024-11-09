package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.responses.UserResponse;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResponseMapper implements Mapper<UserEntity, UserResponse> {
    private final ModelMapper modelMapper;

    @Override
    public UserResponse mapToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

    @Override
    public UserEntity mapFromDto(UserResponse userResponse) {
        return modelMapper.map(userResponse, UserEntity.class);
    }
}
