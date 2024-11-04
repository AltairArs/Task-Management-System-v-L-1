package com.example.taskmanagementsystem.domain.models.jpa;

import com.example.taskmanagementsystem.enums.TaskProgressEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "task_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskListEntity taskList;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress", nullable = false, length = 20)
    @Builder.Default
    private TaskProgressEnum taskProgress = TaskProgressEnum.NOT_STARTED;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_task_id")
    private TaskEntity parentTask;

    @Builder.Default
    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> childTasks = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments = new LinkedHashSet<>();

}