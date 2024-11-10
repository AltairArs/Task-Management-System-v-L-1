package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.responses.TaskListResponseForUser;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskListForUserMapper implements Mapper<TaskListEntity, TaskListResponseForUser> {
    private final UserForUserMapper userForUserMapper;
    @Override
    public TaskListResponseForUser mapToDto(TaskListEntity taskListEntity) {
        return TaskListResponseForUser.builder()
                .name(taskListEntity.getName())
                .owner(userForUserMapper.mapToDto(taskListEntity.getOwner()))
                .build();
    }

    @Override
    public TaskListEntity mapFromDto(TaskListResponseForUser taskListResponseForUser) {
        return null;
    }
}
