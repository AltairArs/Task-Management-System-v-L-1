package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.CommentMapper;
import com.example.taskmanagementsystem.domain.mappers.impl.TaskMapper;
import com.example.taskmanagementsystem.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;

    @GetMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canSeeTask(principal, #id)")
    public ResponseEntity<?> getTask(@PathVariable long id) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.getTask(id)));
    }

    @GetMapping("{id:[0-9]+}/comments")
    @PreAuthorize("@AccessService.canSeeTask(principal, #id)")
    public ResponseEntity<?> getTaskComments(@PathVariable long id) {
        return ResponseEntity.ok(taskService.getTask(id).getComments().stream().map(commentMapper::mapToDto).toList());
    }

    @PostMapping("{taskListId:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditTaskList(principal, #taskListId)")
    public ResponseEntity<?> addTask(@PathVariable long taskListId, @RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.createTask(taskListId, request)));
    }

    @PutMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditTask(principal, #id)")
    public ResponseEntity<?> updateTask(@PathVariable long id, @RequestBody @Valid TaskUpdateRequest request) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.updateTask(id, request)));
    }

    @DeleteMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditTask(principal, #id)")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
