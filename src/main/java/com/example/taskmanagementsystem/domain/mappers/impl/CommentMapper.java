package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.CommentDto;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper implements Mapper<CommentEntity, CommentDto> {
    private final ModelMapper modelMapper;

    @Override
    public CommentDto mapToDto(CommentEntity commentEntity) {
        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Override
    public CommentEntity mapFromDto(CommentDto commentDto) {
        return modelMapper.map(commentDto, CommentEntity.class);
    }
}
