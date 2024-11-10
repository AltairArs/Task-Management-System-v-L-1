package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.requests.MemberAddRequest;
import com.example.taskmanagementsystem.domain.dto.requests.MemberUpdateRoleRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;

public interface TaskListService {
    public abstract TaskListEntity getTaskListById(long id);
    public abstract TaskListEntity createTaskList(UserEntity user, TaskListCreateRequest request);
    public abstract TaskListEntity updateTaskList(long id, TaskListUpdateRequest request);
    public abstract void deleteTaskList(long id);
    public abstract void addMember(long taskListId, MemberAddRequest request);
    public abstract void changeMemberRole(long taskListId, long userId, MemberUpdateRoleRequest request);
    public abstract void deleteMember(long taskListId, long userId);
}
