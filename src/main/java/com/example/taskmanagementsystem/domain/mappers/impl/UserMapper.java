package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.UserDto;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEntity, UserDto> {
    private final ModelMapper modelMapper;

    @Override
    public UserDto mapToDto(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserEntity mapFromDto(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
