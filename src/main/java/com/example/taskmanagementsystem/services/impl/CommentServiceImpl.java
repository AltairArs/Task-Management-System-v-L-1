package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.CommentEntity;
import com.example.taskmanagementsystem.domain.models.jpa.TaskEntity;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.CommentRepository;
import com.example.taskmanagementsystem.services.CommentService;
import com.example.taskmanagementsystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;

    @Override
    public CommentEntity createComment(long taskId, CommentCreateRequest request) {
        TaskEntity taskEntity = taskService.getTask(taskId);
        CommentEntity commentEntity = CommentEntity.builder()
                .owner(taskEntity.getTaskList().getOwner())
                .task(taskEntity)
                .content(request.getContent())
                .build();
        return commentRepository.save(commentEntity);
    }

    @Override
    public CommentEntity updateComment(long commentId, CommentUpdateRequest request) {
        CommentEntity commentEntity = getComment(commentId);
        commentEntity.setContent(request.getContent());
        return commentRepository.save(commentEntity);
    }

    @Override
    public CommentEntity getComment(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new TaskManagementException("Comment not found with id " + commentId));
    }

    @Override
    public List<CommentEntity> getComments(long taskId) {
        return taskService.getTask(taskId).getComments().stream().toList();
    }

    @Override
    public void deleteComment(long commentId) {
        CommentEntity commentEntity = getComment(commentId);
        commentRepository.delete(commentEntity);
    }
}
