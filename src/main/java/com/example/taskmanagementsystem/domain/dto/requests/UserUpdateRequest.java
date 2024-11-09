package com.example.taskmanagementsystem.domain.dto.requests;

import jakarta.validation.constraints.NotBlank;
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
public class UserUpdateRequest implements Serializable {
    @Size(message = "Пароль должен состоять из минимум 8 символов", min = 8)
    @NotBlank(message = "Пароль не может быть пустым")
    String password;
}