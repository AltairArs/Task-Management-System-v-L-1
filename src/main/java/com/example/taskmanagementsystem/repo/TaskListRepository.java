package com.example.taskmanagementsystem.repo;

import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskListEntity, Long> {
    boolean existsByNameAndOwner(String taskListName, UserEntity owner);
}