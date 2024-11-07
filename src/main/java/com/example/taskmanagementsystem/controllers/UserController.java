package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.mappers.impl.UserMapper;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Все пользователи")
    @GetMapping("all/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(userMapper::mapToDto).toList());
    }

    @Operation(summary = "Получить пользователя")
    @GetMapping("{id:[0-9]+}/")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userMapper.mapToDto(userService.getUserById(id)));
    }

    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("current/")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(userMapper.mapToDto(user));
    }
}
