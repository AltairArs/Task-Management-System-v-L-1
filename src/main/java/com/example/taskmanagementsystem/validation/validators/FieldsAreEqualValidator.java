package com.example.taskmanagementsystem.validation.validators;

import com.example.taskmanagementsystem.validation.annotations.FieldsAreEqual;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldsAreEqualValidator implements ConstraintValidator<FieldsAreEqual, Object> {

    private String[] fields;

    @Override
    public void initialize(FieldsAreEqual constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (this.fields.length == 0){
            throw new RuntimeException("Не выбраны поля для валидатора FieldsAreEqual");
        }
        String val = null;
        for (String field: this.fields){
            String value = (String)new BeanWrapperImpl(o).getPropertyValue(field);
            if (val == null) {
                val = value;
            }
            if (Objects.equals(val, value)) {
                val = value;
            } else {
                return false;
            }
        }
        return true;
    }
}
