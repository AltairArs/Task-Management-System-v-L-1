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
@Schema(description = "Запрос на создание списка задач", requiredProperties = {"name"})
public class TaskListCreateRequest {
    @Size(min = 3)
    @Schema(description = "Название нового списка задач", example = "ToDo List")
    String name;
}
