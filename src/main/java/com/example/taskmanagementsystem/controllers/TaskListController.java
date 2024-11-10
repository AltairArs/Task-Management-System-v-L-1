package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.TaskListCreateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.TaskListForAdminMapper;
import com.example.taskmanagementsystem.domain.mappers.impl.TaskListForUserMapper;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.enums.UserRoleEnum;
import com.example.taskmanagementsystem.services.TaskListService;
import com.example.taskmanagementsystem.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task-lists/")
@Tag(
        name = "Task List Controller",
        description = "Контроллер списков задач"
)
public class TaskListController {
    private final TaskListForAdminMapper taskListForAdminMapper;
    private final TaskListForUserMapper taskListForUserMapper;
    private final TaskListService taskListService;
    private final UserService userService;

    @GetMapping("my/")
    public ResponseEntity<?> getMyTaskLists(@AuthenticationPrincipal UserEntity user){
        if (user.getRole() == UserRoleEnum.ADMIN) {
            return ResponseEntity.ok(user.getTaskLists().stream().map(taskListForAdminMapper::mapToDto).toList());
        } else {
            return ResponseEntity.ok(user.getTaskLists().stream().map(taskListForUserMapper::mapToDto).toList());
        }
    }

    @GetMapping("as-member/")
    public ResponseEntity<?> getAsMemberTaskLists(@AuthenticationPrincipal UserEntity user){
        if (user.getRole() == UserRoleEnum.ADMIN) {
            return ResponseEntity.ok(user.getTaskListsAsMember().stream().map(asMember -> taskListForAdminMapper.mapToDto(asMember.getTaskList())).toList());
        } else {
            return ResponseEntity.ok(user.getTaskListsAsMember().stream().map(asMember -> taskListForUserMapper.mapToDto(asMember.getTaskList())).toList());
        }
    }

    @PutMapping
    public ResponseEntity<?> createTaskList(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid TaskListCreateRequest request){
        TaskListEntity taskListEntity = taskListService.createTaskList(user, request);
        if (user.getRole() == UserRoleEnum.ADMIN){
            return new ResponseEntity<>(taskListForAdminMapper.mapToDto(taskListEntity), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(taskListForUserMapper.mapToDto(taskListEntity), HttpStatus.CREATED);
        }
    }

    @GetMapping("{id:[0-9]+}/")
    public ResponseEntity<?> getTaskListById(@AuthenticationPrincipal UserEntity user, @PathVariable long id){
        if (user.getRole() == UserRoleEnum.ADMIN){
            return ResponseEntity.ok(taskListForAdminMapper.mapToDto(taskListService.getById(id)));
        } else {
            return ResponseEntity.ok(taskListForUserMapper.mapToDto(taskListService.getById(id)));
        }
    }
}
