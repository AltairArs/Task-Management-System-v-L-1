package com.example.taskmanagementsystem.repo;

import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.TaskListMemberEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskListMemberRepository extends JpaRepository<TaskListMemberEntity, Long> {
    Optional<TaskListMemberEntity> findByTaskListAndUser(TaskListEntity taskListEntity, UserEntity user);
}