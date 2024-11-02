package com.example.taskmanagementsystem.validation.annotations;

import com.example.taskmanagementsystem.validation.validators.OnlyLettersAndNumbersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OnlyLettersAndNumbersValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyLettersAndNumbers {
    String message() default "должно содержать только 0-9, a-z, A-Z и '_'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
