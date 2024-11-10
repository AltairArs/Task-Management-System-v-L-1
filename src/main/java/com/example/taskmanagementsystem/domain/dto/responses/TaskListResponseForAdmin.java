package com.example.taskmanagementsystem.domain.dto.responses;

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
public class TaskListResponseForAdmin implements Serializable {
    Long id;
    String name;
    UserResponseForAdmin owner;
}