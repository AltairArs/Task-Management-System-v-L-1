package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddRequest {
    @Size(message = "Email может содержать максимум 255 символов", max = 255)
    @Email(message = "Email должен быть в формате example@example.example")
    @NotBlank(message = "Email не может быть пустым")
    String email;
    @NotBlank
    private TaskListMemberRoleEnum role;
}
