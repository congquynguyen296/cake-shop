package com.cakeshop.api_main.constant;

public class BaseConstant {
    // GROUP
    public static final Integer GROUP_KIND_ADMIN = 1;
    public static final Integer GROUP_KIND_CUSTOMER = 2;

    // PRODUCT
    public static final Integer PRODUCT_STATUS_SELLING = 1;
    public static final Integer PRODUCT_STATUS_STOP_SELLING = 2;

    // NATION
    public static final Integer NATION_KIND_PROVINCE = 1;
    public static final Integer NATION_KIND_DISTRICT = 2;
    public static final Integer NATION_KIND_COMMUNE = 3;

    // ORDER STATUS
    public static final Integer ORDER_STATUS_PENDING = 1;
    public static final Integer ORDER_STATUS_PROCESSING = 2;
    public static final Integer ORDER_STATUS_SHIPPING = 3;
    public static final Integer ORDER_STATUS_DELIVERED = 4;
    public static final Integer ORDER_STATUS_CANCELED = 5;

    public static final String PHONE_PATTERN = "^0[35789][0-9]{8}$";
    public static final String EMAIL_PATTERN = "^(?!.*[.]{2,})[a-zA-Z0-9.%]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String USERNAME_PATTERN = "^(?=.{3,20}$)(?!.*[_.]{2})[a-zA-Z][a-zA-Z0-9_]*[a-zA-Z0-9]$";
}
