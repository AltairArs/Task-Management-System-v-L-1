package com.example.taskmanagementsystem.domain.dto.responses;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
