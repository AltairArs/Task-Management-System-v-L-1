package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.enums.TaskProgressEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание задачи", requiredProperties = {"name", "taskProgress"})
public class TaskCreateRequest {
    @Schema(description = "Название задачи", example = "Example")
    @Size(min = 3)
    String name;
    @Schema(description = "Описание задачи", example = "Example")
    String description;
    @NotBlank
    @Schema(description = "Прогресс задачи", example = "NOT_STARTED")
    TaskProgressEnum taskProgress;
}
