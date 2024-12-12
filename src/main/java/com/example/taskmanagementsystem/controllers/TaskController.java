package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.CommentMapper;
import com.example.taskmanagementsystem.domain.mappers.impl.TaskMapper;
import com.example.taskmanagementsystem.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks/")
@RequiredArgsConstructor
@Tag(
        name = "Task Controller",
        description = "Работа с задачами"
)
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить задачу")
    @GetMapping("{id:[0-9]+}/")
    @PreAuthorize("@accessService.canSeeTask(principal, #id)")
    public ResponseEntity<?> getTask(@Parameter(description = "id задачи", example = "123") @PathVariable long id) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.getTask(id)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить комментарии задачи")
    @GetMapping("{id:[0-9]+}/comments")
    @PreAuthorize("@accessService.canSeeTask(principal, #id)")
    public ResponseEntity<?> getTaskComments(@Parameter(description = "id задачи", example = "123") @PathVariable long id) {
        return ResponseEntity.ok(taskService.getTask(id).getComments().stream().map(commentMapper::mapToDto).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавить задачу")
    @PostMapping("{taskListId:[0-9]+}/")
    @PreAuthorize("@accessService.canEditTaskList(principal, #taskListId)")
    public ResponseEntity<?> addTask(@Parameter(description = "id списка задач", example = "123") @PathVariable long taskListId, @RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.createTask(taskListId, request)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Изменить задачу")
    @PutMapping("{id:[0-9]+}/")
    @PreAuthorize("@accessService.canEditTask(principal, #id)")
    public ResponseEntity<?> updateTask(@Parameter(description = "id задачи", example = "123") @PathVariable long id, @RequestBody @Valid TaskUpdateRequest request) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.updateTask(id, request)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удалить задачу")
    @DeleteMapping("{id:[0-9]+}/")
    @PreAuthorize("@accessService.canEditTask(principal, #id)")
    public ResponseEntity<?> deleteTask(@Parameter(description = "id задачи", example = "123") @PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
