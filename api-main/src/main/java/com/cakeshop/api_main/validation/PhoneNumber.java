package com.cakeshop.api_main.validation;

import com.cakeshop.api_main.validation.impl.PhoneNumberValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidation.class)
@Documented
public @interface PhoneNumber {
    String message() default "Phone number is invalid.";

    boolean allowNull() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
