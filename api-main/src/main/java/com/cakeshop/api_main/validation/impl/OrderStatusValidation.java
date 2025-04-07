package com.cakeshop.api_main.validation.impl;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.validation.OrderStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class OrderStatusValidation implements ConstraintValidator<OrderStatus, Integer> {
    private static final Set<Integer> VALID_VALUES = Set.of(
            BaseConstant.ORDER_STATUS_PENDING,
            BaseConstant.ORDER_STATUS_PROCESSING,
            BaseConstant.ORDER_STATUS_SHIPPING,
            BaseConstant.ORDER_STATUS_DELIVERED,
            BaseConstant.ORDER_STATUS_CANCELED
    );

    private boolean allowNull;

    @Override
    public void initialize(OrderStatus constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        return status == null ? allowNull : VALID_VALUES.contains(status);
    }
}
