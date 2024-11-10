package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper implements Mapper<TaskEntity, TaskDto> {
    private final ModelMapper modelMapper;

    @Override
    public TaskDto mapToDto(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskEntity mapFromDto(TaskDto taskDto) {
        return modelMapper.map(taskDto, TaskEntity.class);
    }
}
