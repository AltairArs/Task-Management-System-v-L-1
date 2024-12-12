package com.example.taskmanagementsystem.domain.dto.requests;

import com.example.taskmanagementsystem.enums.TaskListMemberRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение роли участника", requiredProperties = {"role"})
public class MemberUpdateRoleRequest {
    @NotNull
    @Schema(description = "Новая роль", example = "EDITOR")
    private TaskListMemberRoleEnum role;
}
