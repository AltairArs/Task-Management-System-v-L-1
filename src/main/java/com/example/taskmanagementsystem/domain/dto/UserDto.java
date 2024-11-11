package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.enums.UserRoleEnum;
import io.netty.channel.ChannelHandler;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Отображение пользователя")
public class UserDto implements Serializable {
    @Schema(description = "id пользователя")
    Long id;
    @Schema(description = "Email")
    String email;
    @Schema(description = "Роль пользователя", examples = {"USER", "ADMIN"})
    UserRoleEnum role;
    @Schema(description = "Дата создания")
    LocalDateTime createdAt;
    @Schema(description = "Дата последней аутентификации")
    LocalDateTime lastLogin;
}