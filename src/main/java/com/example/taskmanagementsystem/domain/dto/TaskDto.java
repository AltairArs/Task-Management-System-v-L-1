package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.enums.TaskProgressEnum;
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
public class TaskDto implements Serializable {
    Long id;
    String name;
    String description;
    TaskProgressEnum taskProgress;
}