package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;

public interface TaskListService {
    public abstract TaskListEntity createTaskList(UserEntity owner, TaskListCreateRequest taskListCreateRequest);
    public abstract TaskListEntity getById(long id);
    public abstract TaskListEntity getByNameAndOwner(String name, UserEntity owner);
}
