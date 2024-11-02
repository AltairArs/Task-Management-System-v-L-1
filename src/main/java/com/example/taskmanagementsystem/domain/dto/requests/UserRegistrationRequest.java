package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.validation.annotations.FieldsAreEqual;
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
@FieldsAreEqual(
        fields = {
                "password",
                "confirmPassword"
        },
        message = "Пароли должны совпадать"
)
public class UserRegistrationRequest implements Serializable {
    @Size(message = "Email может содержать максимум 255 символов", max = 255)
    @Email(message = "Email должен быть в формате example@example.example")
    @NotBlank(message = "Email не может быть пустым")
    String email;
    @Size(message = "Пароль должен состоять из минимум 8 символов", min = 8)
    @NotBlank(message = "Пароль не может быть пустым")
    String password;
    @Size(message = "Пароль должен состоять из минимум 8 символов", min = 8)
    @NotBlank(message = "Пароль не может быть пустым")
    String confirmPassword;
}