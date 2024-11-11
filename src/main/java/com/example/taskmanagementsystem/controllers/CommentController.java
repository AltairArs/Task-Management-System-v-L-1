package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.CommentMapper;
import com.example.taskmanagementsystem.services.CommentService;
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
@RequestMapping("/api/v1/comments/")
@RequiredArgsConstructor
@Tag(
        name = "Comment Controller",
        description = "Работа с комментариями"
)
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(summary = "Получение комментария")
    @GetMapping("{id:[0-9]+}/")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("@AccessService.canSeeComment(principal, #id)")
    public ResponseEntity<?> getComment(@Parameter(description = "id комментария", example = "123") @PathVariable long id) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.getComment(id)));
    }

    @Operation(summary = "Изменение комментария")
    @PutMapping("{id:[0-9]+}/")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("@AccessService.canEditComment(principal, #id)")
    public ResponseEntity<?> updateComment(@Parameter(description = "id комментария", example = "123") @PathVariable long id, @RequestBody @Valid CommentUpdateRequest request) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.updateComment(id, request)));
    }

    @Operation(summary = "Удаление комментария")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditComment(principal, #id)")
    public ResponseEntity<?> deleteComment(@Parameter(description = "id комментария", example = "123") @PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавление комментария")
    @SecurityRequirement(name = "JWT")
    @PostMapping("{taskId:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditTask(principal, #taskId)")
    public ResponseEntity<?> createComment(@Parameter(description = "id задачи", example = "123") @PathVariable long taskId, @RequestBody @Valid CommentCreateRequest request) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.createComment(taskId, request)));
    }
}
