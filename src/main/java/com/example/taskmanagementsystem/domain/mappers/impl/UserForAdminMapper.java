package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.responses.UserResponseForAdmin;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserForAdminMapper implements Mapper<UserEntity, UserResponseForAdmin> {
    private final ModelMapper modelMapper;

    @Override
    public UserResponseForAdmin mapToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponseForAdmin.class);
    }

    @Override
    public UserEntity mapFromDto(UserResponseForAdmin userResponseForAdmin) {
        return modelMapper.map(userResponseForAdmin, UserEntity.class);
    }
}
