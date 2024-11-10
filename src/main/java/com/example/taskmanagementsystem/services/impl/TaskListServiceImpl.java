package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.MemberAddRequest;
import com.example.taskmanagementsystem.domain.dto.requests.MemberUpdateRoleRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListMemberEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.TaskListMemberRepository;
import com.example.taskmanagementsystem.repo.TaskListRepository;
import com.example.taskmanagementsystem.services.TaskListService;
import com.example.taskmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;
    private final TaskListMemberRepository taskListMemberRepository;
    private final UserService userService;
    private final TaskListService taskListService;

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

    @Override
    public void addMember(long taskListId, MemberAddRequest request) {
        UserEntity user = userService.getUser(request.getEmail());
        TaskListEntity taskListEntity = getTaskListById(taskListId);
        if (taskListMemberRepository.existsByTaskListAndUser(taskListEntity, user)) {
            throw new TaskManagementException("User: " + user.getEmail() + " already is member of task list with id: " + taskListId);
        } else {
            TaskListMemberEntity taskListMemberEntity = TaskListMemberEntity.builder()
                    .taskList(taskListEntity)
                    .role(request.getRole())
                    .user(user)
                    .build();
            taskListMemberRepository.save(taskListMemberEntity);
        }
    }

    @Override
    public void changeMemberRole(long taskListId, long userId, MemberUpdateRoleRequest request) {
        TaskListEntity taskListEntity = getTaskListById(taskListId);
        UserEntity user = userService.getUserById(userId);
        TaskListMemberEntity taskListMemberEntity = taskListMemberRepository.findByTaskListAndUser(
                taskListEntity,
                user
        ).orElseThrow(() -> new TaskManagementException("User: " + user.getEmail() + " is not member of task list with id: " + taskListId));
        taskListMemberEntity.setRole(request.getRole());
        taskListMemberRepository.save(taskListMemberEntity);
    }

    @Override
    public void deleteMember(long taskListId, long userId) {
        TaskListEntity taskListEntity = getTaskListById(taskListId);
        UserEntity user = userService.getUserById(userId);
        TaskListMemberEntity taskListMemberEntity = taskListMemberRepository.findByTaskListAndUser(
                taskListEntity,
                user
        ).orElseThrow(() -> new TaskManagementException("User: " + user.getEmail() + " is not member of task list with id: " + taskListId));
        taskListMemberRepository.delete(taskListMemberEntity);
    }
}
