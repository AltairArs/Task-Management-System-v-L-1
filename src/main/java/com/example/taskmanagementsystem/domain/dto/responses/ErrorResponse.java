package com.example.taskmanagementsystem.domain.dto.responses;

import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    @Builder.Default
    private Map<String, String> fieldErrors = new HashMap<>();
    @Builder.Default
    private List<String> globalErrors = new ArrayList<>();

    public static ErrorResponse fromBindingResult(BindingResult bindingResult){
        return ErrorResponse.builder()
                .fieldErrors(bindingResult.getFieldErrors().stream()
                        .collect(Collectors.toMap(FieldError::getField,
                                err -> Objects.requireNonNullElse(err.getDefaultMessage(), "")))
                )
                .globalErrors(bindingResult.getGlobalErrors().stream()
                        .map(err -> Objects.requireNonNullElse(err.getDefaultMessage(), ""))
                        .collect(Collectors.toList()))
                .build();
    }
    public static ErrorResponse fromString(String s){
        return ErrorResponse.builder()
                .globalErrors(List.of(s))
                .build();
    }
}
