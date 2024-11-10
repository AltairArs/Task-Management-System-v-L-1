package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.repo.TaskListRepository;
import com.example.taskmanagementsystem.services.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    @Override
    public TaskListEntity createTaskList(UserEntity owner, TaskListCreateRequest taskListCreateRequest) {
        if (owner == null) {
            throw new AccessDeniedException("Access Denied");
        } else if (taskListRepository.existsByNameAndOwner(taskListCreateRequest.getName(), owner)) {
            throw new AccessDeniedException("Task list with this name already exists");
        } else {
            TaskListEntity taskListEntity = TaskListEntity.builder()
                    .name(taskListCreateRequest.getName())
                    .owner(owner)
                    .build();
            taskListRepository.save(taskListEntity);
            return taskListEntity;
        }
    }

    @Override
    public TaskListEntity getById(long id) {
        return taskListRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task list not found"));
    }

    @Override
    public TaskListEntity getByNameAndOwner(String name, UserEntity owner) {
        return taskListRepository.findByNameAndOwner(name, owner).orElseThrow(() -> new EntityNotFoundException("Task list not found with name and owner"));
    }
}
