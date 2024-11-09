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
public class UserResponse implements Serializable {
    String email;
    UserRoleEnum role;
    LocalDateTime createdAt;
    LocalDateTime lastLogin;
}