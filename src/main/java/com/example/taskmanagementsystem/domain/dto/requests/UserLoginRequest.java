package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest implements Serializable {
    @Size(message = "Email может содержать максимум 255 символов", max = 255)
    @Email(message = "Email должен быть в формате example@example.example")
    @NotBlank(message = "Email не может быть пустым")
    String email;
    @Size(message = "Пароль должен состоять из минимум 8 символов", min = 8)
    @NotBlank(message = "Пароль не может быть пустым")
    String password;
}