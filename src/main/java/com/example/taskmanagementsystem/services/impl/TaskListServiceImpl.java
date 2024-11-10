package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.TaskListRepository;
import com.example.taskmanagementsystem.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    @Override
    public TaskListEntity getTaskListById(long id) {
        return taskListRepository.findById(id).orElseThrow(() -> new TaskManagementException("Not found task list with id: " + id));
    }

    @Override
    public TaskListEntity createTaskList(UserEntity user, TaskListCreateRequest request) {
        if (taskListRepository.existsByNameAndOwner(request.getName(), user)) {
            throw new TaskManagementException("Task list with name: " + request.getName() + " already exists");
        } else {
            TaskListEntity taskListEntity = TaskListEntity.builder()
                    .owner(user)
                    .name(request.getName())
                    .build();
            taskListRepository.save(taskListEntity);
            return taskListEntity;
        }
    }

    @Override
    public TaskListEntity updateTaskList(long id, TaskListUpdateRequest request) {
        TaskListEntity taskListEntity = getTaskListById(id);
        taskListEntity.setName(request.getName());
        return taskListRepository.save(taskListEntity);
    }

    @Override
    public void deleteTaskList(long id) {
        TaskListEntity taskListEntity = getTaskListById(id);
        taskListRepository.delete(taskListEntity);
    }
}
