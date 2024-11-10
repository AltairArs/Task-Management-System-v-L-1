package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.responses.UserResponseForUser;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserForUserMapper implements Mapper<UserEntity, UserResponseForUser> {
    private final ModelMapper modelMapper;

    @Override
    public UserResponseForUser mapToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponseForUser.class);
    }

    @Override
    public UserEntity mapFromDto(UserResponseForUser userResponseForUser) {
        return modelMapper.map(userResponseForUser, UserEntity.class);
    }
}
