package com.example.taskmanagementsystem.domain.models.pk;

import com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TaskListMemberPrimaryKey {
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskListEntity taskList;

}