package com.example.taskmanagementsystem.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.taskmanagementsystem.domain.models.jpa.CommentEntity}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto implements Serializable {
    Long id;
    LocalDateTime createdAt;
    String content;
    UserDto owner;
}