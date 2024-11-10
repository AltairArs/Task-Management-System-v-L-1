package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskEntity;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.TaskRepository;
import com.example.taskmanagementsystem.services.TaskListService;
import com.example.taskmanagementsystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListService taskListService;

    @Override
    public TaskEntity createTask(long taskListId, TaskCreateRequest request) {
        TaskListEntity taskListEntity = taskListService.getTaskListById(taskListId);
        TaskEntity taskEntity = TaskEntity.builder()
                .taskProgress(request.getTaskProgress())
                .taskList(taskListEntity)
                .name(request.getName())
                .description(request.getDescription())
                .build();
        taskRepository.save(taskEntity);
        return taskEntity;
    }

    @Override
    public TaskEntity getTask(long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskManagementException("Not found task with id: " + taskId));
    }

    @Override
    public List<TaskEntity> getTasks(long taskListId) {
        return taskListService.getTaskListById(taskListId).getTasks().stream().toList();
    }

    @Override
    public TaskEntity updateTask(long taskId, TaskUpdateRequest request) {
        TaskEntity taskEntity = getTask(taskId);
        taskEntity.setTaskProgress(request.getTaskProgress());
        taskEntity.setName(request.getName());
        taskEntity.setDescription(request.getDescription());
        return taskRepository.save(taskEntity);
    }

    @Override
    public void deleteTask(long taskId) {
        TaskEntity taskEntity = getTask(taskId);
        taskRepository.delete(taskEntity);
    }
}
