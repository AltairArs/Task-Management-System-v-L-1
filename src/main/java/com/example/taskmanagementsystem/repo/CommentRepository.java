package com.example.taskmanagementsystem.repo;

import com.example.taskmanagementsystem.domain.models.jpa.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}