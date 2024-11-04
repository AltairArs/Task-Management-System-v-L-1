package com.example.taskmanagementsystem.domain.models.jpa;

import com.example.taskmanagementsystem.domain.models.pk.TaskListMemberPrimaryKey;
import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "task_list_members_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(TaskListMemberPrimaryKey.class)
public class TaskListMemberEntity {
    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private UserEntity user;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskListEntity taskList;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    @Builder.Default
    private TaskListMemberRoleEnum role = TaskListMemberRoleEnum.VIEWER;

}