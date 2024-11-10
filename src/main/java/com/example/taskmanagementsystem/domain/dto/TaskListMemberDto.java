package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.taskmanagementsystem.domain.models.jpa.TaskListMemberEntity}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskListMemberDto implements Serializable {
    Long id;
    TaskListMemberRoleEnum role;
    UserDto user;
}