package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.enums.TaskProgressEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    @Size(min = 3)
    String name;
    String description;
    @NotBlank
    TaskProgressEnum taskProgress;
}
