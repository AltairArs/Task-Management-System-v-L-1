package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.responses.TaskListResponseForAdmin;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskListForAdminMapper implements Mapper<TaskListEntity, TaskListResponseForAdmin> {
    private final UserForAdminMapper userForAdminMapper;
    @Override
    public TaskListResponseForAdmin mapToDto(TaskListEntity taskListEntity) {
        return TaskListResponseForAdmin.builder()
                .id(taskListEntity.getId())
                .name(taskListEntity.getName())
                .owner(userForAdminMapper.mapToDto(taskListEntity.getOwner()))
                .build();
    }

    @Override
    public TaskListEntity mapFromDto(TaskListResponseForAdmin taskListResponseForAdmin) {
        return null;
    }
}
