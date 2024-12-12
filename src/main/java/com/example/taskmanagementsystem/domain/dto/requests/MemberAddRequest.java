package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на добавление нового участника", requiredProperties = {"email", "role"})
public class MemberAddRequest {
    @Size(message = "Email может содержать максимум 255 символов", max = 255)
    @Email(message = "Email должен быть в формате example@example.example")
    @NotNull(message = "Email не может быть пустым")
    @Schema(description = "Email нового участника", example = "example@example.example")
    String email;
    @NotNull
    @Schema(description = "Роль нового участника", example = "VIEWER")
    private TaskListMemberRoleEnum role;
}
