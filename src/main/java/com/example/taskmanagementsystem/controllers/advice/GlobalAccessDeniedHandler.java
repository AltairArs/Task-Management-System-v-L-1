package com.example.taskmanagementsystem.controllers.advice;

import com.example.taskmanagementsystem.domain.dto.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAccessDeniedHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handle(AccessDeniedException exception){
        return new ResponseEntity<>(ErrorResponse.fromString(exception.getMessage()), HttpStatus.FORBIDDEN);
    }
}
