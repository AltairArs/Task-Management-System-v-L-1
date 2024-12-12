package com.example.taskmanagementsystem.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.taskmanagementsystem.domain.models.jpa.UserEntity}
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение пользователя", requiredProperties = {"password"})
public class UserUpdateRequest implements Serializable {
    @Size(message = "Пароль должен состоять из минимум 8 символов", min = 8)
    @NotNull(message = "Пароль не может быть пустым")
    @Schema(description = "Новый пароль")
    String password;
}