package com.cakeshop.api_main.validation;

import com.cakeshop.api_main.validation.impl.OrderStatusValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderStatusValidation.class)
@Documented
public @interface OrderStatus {
    boolean allowNull() default false;

    String message() default "Status invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
