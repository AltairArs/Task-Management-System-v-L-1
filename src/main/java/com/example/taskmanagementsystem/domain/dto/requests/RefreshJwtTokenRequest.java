package com.example.taskmanagementsystem.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на обновление токенов", requiredProperties = {"refreshToken"})
public class RefreshJwtTokenRequest {
    @Schema(description = "Токен обновления", example = "asdkljshjkfgsgfhdgsjfhshfuinuycyrwuryciu4yrey7y7378y4y3")
    @NotNull(message = "Refresh Token должен быть передан в запросе")
    private String refreshToken;
}
