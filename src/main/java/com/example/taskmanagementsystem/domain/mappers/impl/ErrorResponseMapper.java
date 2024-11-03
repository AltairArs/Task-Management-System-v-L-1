package com.example.taskmanagementsystem.domain.mappers.impl;

import com.example.taskmanagementsystem.domain.dto.responses.ErrorResponse;
import com.example.taskmanagementsystem.domain.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ErrorResponseMapper implements Mapper<BindingResult, ErrorResponse> {

    @Override
    public ErrorResponse mapToDto(BindingResult bindingResult) {
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

    @Override
    public BindingResult mapFromDto(ErrorResponse errorResponse) {
        return null;
    }
}
