package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskEntity;

import java.util.List;

public interface TaskService {
    public abstract TaskEntity createTask(long taskListId, TaskCreateRequest request);
    public abstract TaskEntity getTask(long taskId);
    public abstract List<TaskEntity> getTasks(long taskListId);
    public abstract TaskEntity updateTask(long taskId, TaskUpdateRequest request);
    public abstract void deleteTask(long taskId);
}
