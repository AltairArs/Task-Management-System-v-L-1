package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;

public interface TaskListService {
    public abstract TaskListEntity getTaskListById(long id);
    public abstract TaskListEntity createTaskList(UserEntity user, TaskListCreateRequest request);
    public abstract TaskListEntity updateTaskList(long id, TaskListUpdateRequest request);
    public abstract void deleteTaskList(long id);
}
