package com.example.taskmanagementsystem.domain.dto.responses;

import com.example.taskmanagementsystem.enums.UserRoleEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.taskmanagementsystem.domain.models.jpa.UserEntity}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseForAdmin implements Serializable {
    private Long id;
    private String email;
    private UserRoleEnum role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}