package com.example.taskmanagementsystem.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на изменение комментария", requiredProperties = {"content"})
public class CommentUpdateRequest {
    @Schema(description = "Новое содержание", example = "Example")
    String content;
}
