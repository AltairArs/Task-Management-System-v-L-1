package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.TaskListMemberDto;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskListMemberMapper implements Mapper<TaskListMemberEntity, TaskListMemberDto> {
    private final UserMapper userMapper;

    @Override
    public TaskListMemberDto mapToDto(TaskListMemberEntity taskListMemberEntity) {
        return TaskListMemberDto.builder()
                .id(taskListMemberEntity.getId())
                .role(taskListMemberEntity.getRole())
                .user(userMapper.mapToDto(taskListMemberEntity.getUser()))
                .build();
    }

    @Override
    public TaskListMemberEntity mapFromDto(TaskListMemberDto taskListMemberDto) {
        return null;
    }
}
