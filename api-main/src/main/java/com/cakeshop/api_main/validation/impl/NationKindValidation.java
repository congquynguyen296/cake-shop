package com.cakeshop.api_main.validation.impl;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.validation.NationKind;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class NationKindValidation implements ConstraintValidator<NationKind, Integer> {
    private static final Set<Integer> VALID_VALUES = Set.of(
            BaseConstant.NATION_KIND_PROVINCE,
            BaseConstant.NATION_KIND_DISTRICT,
            BaseConstant.NATION_KIND_COMMUNE
    );
    private boolean allowNull;

    @Override
    public void initialize(NationKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer kind, ConstraintValidatorContext constraintValidatorContext) {
        return kind == null ? allowNull : VALID_VALUES.contains(kind);
    }
}
