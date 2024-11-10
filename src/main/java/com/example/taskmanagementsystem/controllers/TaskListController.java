package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.MemberAddRequest;
import com.example.taskmanagementsystem.domain.dto.requests.MemberUpdateRoleRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskListUpdateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.*;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.services.TaskListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task-lists/")
@RequiredArgsConstructor
public class TaskListController {
    private final TaskListMapper taskListMapper;
    private final TaskListService taskListService;
    private final TaskMapper taskMapper;
    private final TaskListMemberMapper taskListMemberMapper;

    @GetMapping("my/")
    public ResponseEntity<?> getMyTaskList(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user.getTaskLists().stream().map(taskListMapper::mapToDto).toList());
    }

    @GetMapping("as-member/")
    public ResponseEntity<?> getAsMemberTaskList(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user.getTaskListsAsMember().stream().map(asMember -> taskListMapper.mapToDto(asMember.getTaskList())).toList());
    }

    @GetMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canSeeTaskList(principal, #id)")
    public ResponseEntity<?> getTaskListById(@PathVariable long id) {
        return ResponseEntity.ok(taskListMapper.mapToDto(taskListService.getTaskListById(id)));
    }

    @GetMapping("{id:[0-9]+}/tasks/")
    @PreAuthorize("@AccessService.canSeeTaskList(principal, #id)")
    public ResponseEntity<?> getTaskListComments(@PathVariable long id) {
        return ResponseEntity.ok(taskListService.getTaskListById(id).getTasks().stream().map(taskMapper::mapToDto).toList());
    }

    @GetMapping("{id:[0-9]+}/members/")
    @PreAuthorize("@AccessService.canSeeTaskList(principal, #id)")
    public ResponseEntity<?> getTaskListMembers(@PathVariable long id) {
        return ResponseEntity.ok(taskListService.getTaskListById(id).getMembers().stream().map(taskListMemberMapper::mapToDto).toList());
    }

    @PostMapping
    public ResponseEntity<?> createTaskList(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid TaskListCreateRequest request) {
        return ResponseEntity.ok(taskListMapper.mapToDto(taskListService.createTaskList(user, request)));
    }

    @PutMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.isOnwerTaskList(principal, #id)")
    public ResponseEntity<?> updateTaskList(@PathVariable long id, @RequestBody @Valid TaskListUpdateRequest request) {
        return ResponseEntity.ok(taskListMapper.mapToDto(taskListService.updateTaskList(id, request)));
    }

    @DeleteMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.isOnwerTaskList(principal, #id)")
    public ResponseEntity<?> deleteTaskList(@PathVariable long id) {
        taskListService.deleteTaskList(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id:[0-9]+}/members/")
    @PreAuthorize("@AccessService.isOnwerTaskList(principal, #id)")
    public ResponseEntity<?> addMemberToTaskList(@PathVariable long id, @RequestBody @Valid MemberAddRequest request) {
        taskListService.addMember(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id:[0-9]+}/{userId:[0-9]+}/")
    @PreAuthorize("@AccessService.isOnwerTaskList(principal, #id)")
    public ResponseEntity<?> updateTaskListMember(@PathVariable long id, @PathVariable long userId, @RequestBody @Valid MemberUpdateRoleRequest request) {
        taskListService.changeMemberRole(id, userId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id:[0-9]+}/{userId:[0-9]+}/")
    @PreAuthorize("@AccessService.isOnwerTaskList(principal, #id)")
    public ResponseEntity<?> deleteTaskListMember(@PathVariable long id, @PathVariable long userId) {
        taskListService.deleteMember(id, userId);
        return ResponseEntity.ok().build();
    }
}
