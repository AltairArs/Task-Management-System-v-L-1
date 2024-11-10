package com.example.taskmanagementsystem.domain.dto.requests;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskListCreateRequest {
    @Size(min = 3)
    String name;
}
