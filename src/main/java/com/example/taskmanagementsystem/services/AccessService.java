package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.TaskListMemberRepository;
import com.example.taskmanagementsystem.repo.TaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessService {
    private final TaskListRepository taskListRepository;
    private final TaskListMemberRepository taskListMemberRepository;

    public boolean canSeeTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new TaskManagementException("Could not find task list with id: " + taskListId));
        return taskListEntity.getOwner().equals(user) || taskListMemberRepository.findByTaskListAndUser(taskListEntity, user).isPresent();
    }

    public boolean isOwnerTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new TaskManagementException("Could not find task list with id: " + taskListId));
        return taskListEntity.getOwner().equals(user);
    }
}
