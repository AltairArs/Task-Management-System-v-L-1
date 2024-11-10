package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskmanagementsystem.domain.dto.responses.ErrorResponse;
import com.example.taskmanagementsystem.domain.mappers.impl.CommentMapper;
import com.example.taskmanagementsystem.domain.mappers.impl.TaskListMapper;
import com.example.taskmanagementsystem.domain.mappers.impl.UserMapper;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.enums.UserRoleEnum;
import com.example.taskmanagementsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
@Tag(
        name = "UserController",
        description = "Контроллер пользователей"
)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final TaskListMapper taskListMapper;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Все пользователи")
    @GetMapping("all/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(userMapper::mapToDto).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить пользователя")
    @GetMapping("{id:[0-9]+}/")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userMapper.mapToDto(userService.getUserById(id)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("current/")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(userMapper.mapToDto(user));
    }

    @GetMapping("{id:[0-9]+}/comments")
    public ResponseEntity<?> getUserComments(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id).getComments().stream().map(commentMapper::mapToDto).toList());
    }

    @GetMapping("current/comments/")
    public ResponseEntity<?> getCurrentUserComments(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user.getComments().stream().map(commentMapper::mapToDto).toList());
    }

    @GetMapping("{id:[0-9]+}/task-lists")
    public ResponseEntity<?> getUserTaskLists(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id).getAllTaskLists().stream().map(taskListMapper::mapToDto).toList());
    }

    @GetMapping("current/task-lists/")
    public ResponseEntity<?> getCurrentUserTaskLists(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user.getAllTaskLists().stream().map(taskListMapper::mapToDto).toList());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Изменение пароля")
    @PutMapping("current/")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userMapper.mapToDto(userService.updateUser(user, userUpdateRequest)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("{id:[0-9]+}/")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
