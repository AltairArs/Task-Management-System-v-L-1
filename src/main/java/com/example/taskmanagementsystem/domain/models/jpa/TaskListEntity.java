package com.example.taskmanagementsystem.domain.models.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "task_list_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "owner_email", nullable = false)
    private UserEntity owner;

    @Builder.Default
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasks = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskListMemberEntity> members = new LinkedHashSet<>();

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumns({
            @JoinColumn(name = "type_owner_email", referencedColumnName = "owner_email"),
            @JoinColumn(name = "type_name", referencedColumnName = "name")
    })
    private TaskTypeEntity taskType;

}