package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.enums.TaskProgressEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.taskmanagementsystem.domain.models.jpa.TaskEntity}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Отображение задачи")
public class TaskDto implements Serializable {
    @Schema(description = "id задачи")
    Long id;
    @Schema(description = "Название задачи")
    String name;
    @Schema(description = "Описание задачи")
    String description;
    @Schema(description = "Прогресс выполнения")
    TaskProgressEnum taskProgress;
}