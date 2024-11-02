package com.example.taskmanagementsystem.validation.validators;

import com.example.taskmanagementsystem.validation.annotations.OnlyLettersAndNumbers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnlyLettersAndNumbersValidator implements ConstraintValidator<OnlyLettersAndNumbers, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.matches("^[\\w]+$") || s == null || s.isEmpty();
    }
}
