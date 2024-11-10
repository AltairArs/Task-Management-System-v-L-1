package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskmanagementsystem.domain.dto.responses.ErrorResponse;
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
