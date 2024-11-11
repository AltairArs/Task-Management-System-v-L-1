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
@Schema(description = "Запрос на изменение задачи", requiredProperties = {"name", "taskProgress"})
public class TaskUpdateRequest {
    @Size(min = 3)
    @Schema(description = "Новое название задачи", example = "Example")
    String name;
    @Schema(description = "Новое описание задачи", example = "Example")
    String description;
    @NotBlank
    @Schema(description = "Новое состояние прогресса выполнения задачи", example = "FINISHED")
    TaskProgressEnum taskProgress;
}
