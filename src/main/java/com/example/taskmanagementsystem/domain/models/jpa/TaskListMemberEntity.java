package com.example.taskmanagementsystem.domain.models.jpa;

import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_list_member_entity")
public class TaskListMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private TaskListMemberRoleEnum role = TaskListMemberRoleEnum.VIEWER;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskListEntity taskList;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}