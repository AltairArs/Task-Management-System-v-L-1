package com.example.taskmanagementsystem.domain.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ с токенами")
public class JwtAuthenticationResponse {
    @Schema(description = "Токен доступа")
    private String accessToken;
    @Schema(description = "Токен обновления")
    private String refreshToken;
}
