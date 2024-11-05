package com.example.taskmanagementsystem.domain.models.jpa;

import com.example.taskmanagementsystem.domain.models.pk.TaskTypePrimaryKey;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "task_type_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(TaskTypePrimaryKey.class)
public class TaskTypeEntity {
  @Id
  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "owner_email", nullable = false)
  private UserEntity owner;

  @Id
  @Column(name = "name", nullable = false)
  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "taskType", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TaskEntity> tasks = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "taskType", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TaskListEntity> taskLists = new LinkedHashSet<>();

}