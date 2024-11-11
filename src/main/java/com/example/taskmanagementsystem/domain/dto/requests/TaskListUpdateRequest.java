package com.example.taskmanagementsystem.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение списка задач", requiredProperties = {"name"})
public class TaskListUpdateRequest {
    @Size(min = 3)
    @Schema(description = "Новое название списка задач")
    String name;
}
