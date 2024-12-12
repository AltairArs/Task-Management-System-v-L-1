package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на аутентификацию пользователя", requiredProperties = {"email, password"})
public class UserLoginRequest implements Serializable {
    @Size(message = "Email может содержать максимум 255 символов", max = 255)
    @Email(message = "Email должен быть в формате example@example.example")
    @NotNull(message = "Email не может быть пустым")
    @Schema(description = "Email", example = "example@example.example")
    String email;
    @Size(message = "Пароль должен состоять из минимум 8 символов", min = 8)
    @NotNull(message = "Пароль не может быть пустым")
    @Schema(description = "Пароль")
    String password;
}