package com.example.taskmanagementsystem.domain.models.jpa;

import com.example.taskmanagementsystem.enums.TaskProgressEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_entity")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress_enum", nullable = false, length = 20)
    private TaskProgressEnum taskProgress = TaskProgressEnum.NOT_STARTED;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private TaskListEntity taskList;

    @OneToMany(mappedBy = "task", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CommentEntity> comments = new LinkedHashSet<>();

}