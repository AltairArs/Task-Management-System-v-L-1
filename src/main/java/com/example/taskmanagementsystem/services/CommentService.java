package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskmanagementsystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskmanagementsystem.domain.models.jpa.CommentEntity;

import java.util.List;

public interface CommentService {
    public abstract CommentEntity createComment(long taskId, CommentCreateRequest request);
    public abstract CommentEntity updateComment(long commentId, CommentUpdateRequest request);
    public abstract CommentEntity getComment(long commentId);
    public abstract List<CommentEntity> getComments(long taskId);
    public abstract void deleteComment(long commentId);
}
