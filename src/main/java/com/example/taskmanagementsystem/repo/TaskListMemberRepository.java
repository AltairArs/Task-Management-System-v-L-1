package com.example.taskmanagementsystem.repo;

import com.example.taskmanagementsystem.domain.models.jpa.TaskListMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListMemberRepository extends JpaRepository<TaskListMemberEntity, Long> {
}