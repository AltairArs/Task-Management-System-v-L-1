package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.MemberAddRequest;
import com.example.taskmanagementsystem.domain.dto.requests.MemberUpdateRoleRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListUpdateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.*;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.services.TaskListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task-lists/")
@RequiredArgsConstructor
@Tag(
        name = "Task List Controller",
        description = "Работа со списками задач"
)
public class TaskListController {
    private final TaskListMapper taskListMapper;
    private final TaskListService taskListService;
    private final TaskMapper taskMapper;
    private final TaskListMemberMapper taskListMemberMapper;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить списки задач, где пользователь является владельцем")
    @GetMapping("my/")
    public ResponseEntity<?> getMyTaskList(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user.getTaskLists().stream().map(taskListMapper::mapToDto).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить списки задач, где пользователь является участником")
    @GetMapping("as-member/")
    public ResponseEntity<?> getAsMemberTaskList(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user.getTaskListsAsMember().stream().map(asMember -> taskListMapper.mapToDto(asMember.getTaskList())).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить список задач")
    @GetMapping("{id:[0-9]+}/")
    @PreAuthorize("@accessService.canSeeTaskList(principal, #id)")
    public ResponseEntity<?> getTaskListById(@Parameter(description = "id списка задач", example = "123") @PathVariable long id) {
        return ResponseEntity.ok(taskListMapper.mapToDto(taskListService.getTaskListById(id)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить задачи в списке")
    @GetMapping("{id:[0-9]+}/tasks/")
    @PreAuthorize("@accessService.canSeeTaskList(principal, #id)")
    public ResponseEntity<?> getTaskListTasks(@Parameter(description = "id списка задач", example = "123") @PathVariable long id) {
        return ResponseEntity.ok(taskListService.getTaskListById(id).getTasks().stream().map(taskMapper::mapToDto).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить список участников списка задач")
    @GetMapping("{id:[0-9]+}/members/")
    @PreAuthorize("@accessService.canSeeTaskList(principal, #id)")
    public ResponseEntity<?> getTaskListMembers(@Parameter(description = "id списка задач", example = "123") @PathVariable long id) {
        return ResponseEntity.ok(taskListService.getTaskListById(id).getMembers().stream().map(taskListMemberMapper::mapToDto).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание списка задач")
    @PostMapping
    public ResponseEntity<?> createTaskList(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid TaskListCreateRequest request) {
        return ResponseEntity.ok(taskListMapper.mapToDto(taskListService.createTaskList(user, request)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Изменить список задач")
    @PutMapping("{id:[0-9]+}/")
    @PreAuthorize("@accessService.isOwnerTaskList(principal, #id)")
    public ResponseEntity<?> updateTaskList(@Parameter(description = "id списка задач", example = "123") @PathVariable long id, @RequestBody @Valid TaskListUpdateRequest request) {
        return ResponseEntity.ok(taskListMapper.mapToDto(taskListService.updateTaskList(id, request)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удалить список задач")
    @DeleteMapping("{id:[0-9]+}/")
    @PreAuthorize("@accessService.isOwnerTaskList(principal, #id)")
    public ResponseEntity<?> deleteTaskList(@Parameter(description = "id списка задач", example = "123") @PathVariable long id) {
        taskListService.deleteTaskList(id);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавить участника к списку задач")
    @PostMapping("{id:[0-9]+}/members/")
    @PreAuthorize("@accessService.isOwnerTaskList(principal, #id)")
    public ResponseEntity<?> addMemberToTaskList(@Parameter(description = "id списка задач", example = "123") @PathVariable long id, @RequestBody @Valid MemberAddRequest request) {
        taskListService.addMember(id, request);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Изменить роль участника")
    @PutMapping("{id:[0-9]+}/{userId:[0-9]+}/")
    @PreAuthorize("@accessService.isOwnerTaskList(principal, #id)")
    public ResponseEntity<?> updateTaskListMember(@Parameter(description = "id списка задач", example = "123") @PathVariable long id, @Parameter(description = "id участника", example = "123") @PathVariable long userId, @RequestBody @Valid MemberUpdateRoleRequest request) {
        taskListService.changeMemberRole(id, userId, request);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удалить участника")
    @DeleteMapping("{id:[0-9]+}/{userId:[0-9]+}/")
    @PreAuthorize("@accessService.isOwnerTaskList(principal, #id)")
    public ResponseEntity<?> deleteTaskListMember(@Parameter(description = "id списка задач", example = "123") @PathVariable long id, @Parameter(description = "id участника", example = "123") @PathVariable long userId) {
        taskListService.deleteMember(id, userId);
        return ResponseEntity.ok().build();
    }
}
