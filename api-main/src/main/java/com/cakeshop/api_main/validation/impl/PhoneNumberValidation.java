package com.cakeshop.api_main.validation.impl;

import com.cakeshop.api_main.validation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidation implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_PATTERN = "^0[35789][0-9]{8}$";
    private boolean allowNull;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) {
            return allowNull;
        }
        return phone.matches(PHONE_PATTERN);
    }
}
