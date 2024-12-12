package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.models.jpa.*;
import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.CommentRepository;
import com.example.taskmanagementsystem.repo.TaskListMemberRepository;
import com.example.taskmanagementsystem.repo.TaskListRepository;
import com.example.taskmanagementsystem.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessService {
    private final TaskListRepository taskListRepository;
    private final TaskListMemberRepository taskListMemberRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    private TaskEntity getTaskEntity(long taskId){
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskManagementException("Could not find task with id: " + taskId));
    }

    private TaskListEntity getTaskListEntity(long taskListId){
        return taskListRepository.findById(taskListId)
                .orElseThrow(() -> new TaskManagementException("Could not find task list with id: " + taskListId));
    }

    private CommentEntity getCommentEntity(long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new TaskManagementException("Could not find comment with id: " + commentId));
    }

    public boolean canSeeTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = getTaskListEntity(taskListId);
        return Objects.equals(taskListEntity.getOwner().getId(), user.getId()) || taskListMemberRepository.findByTaskListAndUser(taskListEntity, user).isPresent();
    }

    public boolean canEditTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = getTaskListEntity(taskListId);
        if (Objects.equals(taskListEntity.getOwner().getId(), user.getId()))
            return true;
        return taskListMemberRepository.findByTaskListAndUser(taskListEntity, user)
                .orElseThrow(() -> new TaskManagementException("User is not member or owner")).getRole() == TaskListMemberRoleEnum.EDITOR;
    }

    public boolean isOwnerTaskList(UserEntity user, long taskListId) {
        TaskListEntity taskListEntity = getTaskListEntity(taskListId);
        return Objects.equals(taskListEntity.getOwner().getId(), user.getId());
    }

    public boolean canSeeTask(UserEntity user, long taskId) {
        TaskEntity taskEntity = getTaskEntity(taskId);
        return canSeeTaskList(user, taskEntity.getTaskList().getId());
    }

    public boolean canEditTask(UserEntity user, long taskId) {
        TaskEntity taskEntity = getTaskEntity(taskId);
        return canEditTaskList(user, taskEntity.getTaskList().getId());
    }

    public boolean canSeeComments(UserEntity user, long commentId) {
        CommentEntity commentEntity = getCommentEntity(commentId);
        return canSeeTask(user, commentEntity.getTask().getTaskList().getId());
    }

    public boolean canEditComments(UserEntity user, long commentId) {
        CommentEntity commentEntity = getCommentEntity(commentId);
        return canEditTask(user, commentEntity.getTask().getTaskList().getId());
    }
}
