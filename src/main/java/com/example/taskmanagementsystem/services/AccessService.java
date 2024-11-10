package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.models.jpa.TaskEntity;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.TaskListMemberRepository;
import com.example.taskmanagementsystem.repo.TaskListRepository;
import com.example.taskmanagementsystem.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessService {
    private final TaskListRepository taskListRepository;
    private final TaskListMemberRepository taskListMemberRepository;
    private final TaskRepository taskRepository;

    private TaskEntity getTaskEntity(long taskId){
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskManagementException("Could not find task with id: " + taskId));
    }

    private TaskListEntity getTaskListEntity(long taskListId){
        return taskListRepository.findById(taskListId)
                .orElseThrow(() -> new TaskManagementException("Could not find task list with id: " + taskListId));
    }

    public boolean canSeeTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = getTaskListEntity(taskListId);
        return taskListEntity.getOwner().equals(user) || taskListMemberRepository.findByTaskListAndUser(taskListEntity, user).isPresent();
    }

    public boolean isOwnerTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = getTaskListEntity(taskListId);
        return taskListEntity.getOwner().equals(user);
    }

    public boolean canSeeTask(UserEntity user, long taskId) {
        TaskEntity taskEntity = getTaskEntity(taskId);
        return taskListMemberRepository.findByTaskListAndUser(taskEntity.getTaskList(), user).isPresent() || taskEntity.getTaskList().getOwner().equals(user);
    }

    public boolean canEditTask(UserEntity user, long taskId) {
        TaskEntity taskEntity = getTaskEntity(taskId);
        return taskEntity.getTaskList().getOwner().equals(user)
                || taskListMemberRepository.findByTaskListAndUser(taskEntity.getTaskList(), user)
                .orElseThrow(() -> new TaskManagementException("User is not member or owner")).getRole() == TaskListMemberRoleEnum.EDITOR;
    }

    public boolean canEditTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = getTaskListEntity(taskListId);
        return taskListEntity.getOwner().equals(user)
                || taskListMemberRepository.findByTaskListAndUser(taskListEntity, user)
                .orElseThrow(() -> new TaskManagementException("User is not member or owner")).getRole() == TaskListMemberRoleEnum.EDITOR;
    }
}
