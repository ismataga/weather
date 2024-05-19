package com.folksdev.weather.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CItyNameValidator implements ConstraintValidator<CityNameConstraint, String> {
    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.replace("[^A-Za-z0-9", "");
        return  !StringUtils.isNumeric(value) && !StringUtils.isAllBlank(value);

    }
}
