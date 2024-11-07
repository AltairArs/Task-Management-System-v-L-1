package com.example.taskmanagementsystem.domain.models.jpa;

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
@Table(
        name = "task_list_entity",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "owner_id"})
        }
)
public class TaskListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Builder.Default
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasks = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskListMemberEntity> members = new LinkedHashSet<>();

}