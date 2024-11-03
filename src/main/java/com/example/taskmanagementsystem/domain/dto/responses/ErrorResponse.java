package com.example.taskmanagementsystem.domain.dto.responses;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private Map<String, String> fieldErrors;
    private List<String> globalErrors;
}
