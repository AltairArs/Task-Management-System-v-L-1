package com.example.taskmanagementsystem.repo;

import com.example.taskmanagementsystem.domain.models.jpa.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}