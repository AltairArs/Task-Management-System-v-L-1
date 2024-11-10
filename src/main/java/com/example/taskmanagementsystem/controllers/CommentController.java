package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskmanagementsystem.domain.mappers.impl.CommentMapper;
import com.example.taskmanagementsystem.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments/")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canSeeComment(principal, #id)")
    public ResponseEntity<?> getComment(@PathVariable long id) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.getComment(id)));
    }

    @PutMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditComment(principal, #id)")
    public ResponseEntity<?> updateComment(@PathVariable long id, @RequestBody @Valid CommentUpdateRequest request) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.updateComment(id, request)));
    }

    @DeleteMapping("{id:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditComment(principal, #id)")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{taskId:[0-9]+}/")
    @PreAuthorize("@AccessService.canEditTask(principal, #taskId)")
    public ResponseEntity<?> createComment(@PathVariable long taskId, @RequestBody @Valid CommentCreateRequest request) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.createComment(taskId, request)));
    }
}
