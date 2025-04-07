package com.cakeshop.api_main.validation;

import com.cakeshop.api_main.validation.impl.NationKindValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NationKindValidation.class)
@Documented
public @interface NationKind {
    boolean allowNull() default false;

    String message() default "Kind invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
