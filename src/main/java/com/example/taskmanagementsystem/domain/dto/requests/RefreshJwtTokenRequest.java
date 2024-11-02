package com.example.taskmanagementsystem.domain.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshJwtTokenRequest {
    @NotBlank(message = "Refresh Token должен быть передан в запросе")
    private String refreshToken;
}
