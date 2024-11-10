package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.TaskListDto;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskListMapper implements Mapper<TaskListEntity, TaskListDto> {
    private final UserMapper userMapper;

    @Override
    public TaskListDto mapToDto(TaskListEntity taskListEntity) {
        return TaskListDto.builder()
                .name(taskListEntity.getName())
                .owner(userMapper.mapToDto(taskListEntity.getOwner()))
                .id(taskListEntity.getId())
                .build();
    }

    @Override
    public TaskListEntity mapFromDto(TaskListDto taskListDto) {
        return null;
    }
}
