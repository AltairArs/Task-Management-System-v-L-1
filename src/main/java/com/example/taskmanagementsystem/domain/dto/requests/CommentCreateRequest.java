package com.example.taskmanagementsystem.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на создание комментария", requiredProperties = {"content"})
public class CommentCreateRequest {
    @Schema(description = "Содержание комментария", example = "Example")
    String content;
}
