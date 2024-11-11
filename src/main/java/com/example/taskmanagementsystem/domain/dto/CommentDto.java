package com.example.taskmanagementsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Отображение комментария")
public class CommentDto implements Serializable {
    @Schema(description = "id комментария")
    Long id;
    @Schema(description = "Дата создания")
    LocalDateTime createdAt;
    @Schema(description = "Содержание")
    String content;
    @Schema(description = "Автор комментария")
    UserDto owner;
}