package com.example.taskmanagementsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.taskmanagementsystem.domain.models.jpa.TaskListEntity}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Отображение списка задач")
public class TaskListDto implements Serializable {
    @Schema(description = "id списка задач")
    Long id;
    @Schema(description = "Название списка задач")
    String name;
    @Schema(description = "Владелец списка задач")
    UserDto owner;
}