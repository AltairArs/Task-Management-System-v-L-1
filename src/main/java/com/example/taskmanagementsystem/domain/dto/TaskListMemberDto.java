package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Отображение участника")
public class TaskListMemberDto implements Serializable {
    Long id;
    @Schema(description = "Роль участника")
    TaskListMemberRoleEnum role;
    @Schema(description = "Участник")
    UserDto user;
}