package com.cakeshop.api_main.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    RESOURCE_NOT_EXISTED(404, "Resource is not exists", HttpStatus.NOT_FOUND),
    RESOURCE_EXISTED(405, "Resource already exists", HttpStatus.ALREADY_REPORTED),
    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FORM_ERROR(400, "Invalid Form Error", HttpStatus.BAD_REQUEST),
    // CATEGORY
    CATEGORY_NOT_FOUND_ERROR(1001, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_EXISTED_ERROR(1002, "Category name existed", HttpStatus.BAD_REQUEST),
    CATEGORY_CANT_DELETE_RELATIONSHIP_WITH_PRODUCT_ERROR(1003, "Category cannot delete relationship with product", HttpStatus.BAD_REQUEST),

    // TAG
    TAG_NOT_FOUND_ERROR(1004, "Tag not found", HttpStatus.NOT_FOUND),
    TAG_NAME_EXISTED_ERROR(1005, "Tag name existed", HttpStatus.BAD_REQUEST),

    // PRODUCT
    PRODUCT_NOT_FOUND_ERROR(1006, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_NAME_EXISTED_ERROR(1007, "Product name existed", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_NOT_ENOUGH_ERROR(1008, "Product quantity not enough", HttpStatus.BAD_REQUEST),

    // CUSTOMER
    CUSTOMER_NOT_FOUND_ERROR(1009, "Customer not found", HttpStatus.NOT_FOUND),
    CUSTOMER_USERNAME_EXISTED_ERROR(1010, "Customer username existed", HttpStatus.BAD_REQUEST),
    CUSTOMER_EMAIL_EXISTED_ERROR(1011, "Customer email existed", HttpStatus.BAD_REQUEST),
    CUSTOMER_PHONE_NUMBER_EXISTED_ERROR(1012, "Customer phone number existed", HttpStatus.BAD_REQUEST),

    // GROUP
    GROUP_NOT_FOUND_ERROR(1013, "Group not found", HttpStatus.NOT_FOUND),
    GROUP_NAME_EXISTED_ERROR(1014, "Group name existed", HttpStatus.BAD_REQUEST),

    // PERMISSION
    PERMISSION_NOT_FOUND_ERROR(1015, "Permission not found", HttpStatus.NOT_FOUND),
    PERMISSION_NAME_EXISTED_ERROR(1016, "Permission name existed", HttpStatus.BAD_REQUEST),

    // ADDRESS
    ADDRESS_NOT_FOUND_ERROR(1017, "Address not found", HttpStatus.NOT_FOUND),

    // NATION
    NATION_NOT_FOUND_ERROR(1018, "Nation not found", HttpStatus.NOT_FOUND),
    NATION_PARENT_INVALID_ERROR(1019, "Nation parent invalid", HttpStatus.BAD_REQUEST),
    NATION_PARENT_NOT_FOUND_ERROR(1020, "Nation parent not found", HttpStatus.NOT_FOUND),
    NATION_PROVINCE_NOT_HAVE_PARENT_ERROR(1021, "Nation province cannot have parent", HttpStatus.BAD_REQUEST),
    NATION_NAME_EXISTED_ERROR(1022, "Nation name existed", HttpStatus.BAD_REQUEST),
    NATION_CANT_UPDATE_RELATIONSHIP_WITH_ADDRESS_ERROR(1023, "Nation cannot update relationship with address", HttpStatus.BAD_REQUEST),
    NATION_CANT_DELETE_RELATIONSHIP_WITH_ADDRESS_ERROR(1024, "Nation cannot delete relationship with address", HttpStatus.BAD_REQUEST),

    // ORDER
    ORDER_NOT_FOUND_ERROR(1025, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_CANCEL_EXPIRED(1026, "Order can only be canceled within 3 days", HttpStatus.BAD_REQUEST),
    ORDER_STATUS_INVALID_ERROR(1027, "Order status invalid", HttpStatus.BAD_REQUEST),

    // CART
    CART_NOT_FOUND_ERROR(1028, "Cart not found", HttpStatus.NOT_FOUND),

    // CART_ITEM
    CART_ITEM_NOT_FOUND_ERROR(1029, "Cart item not found", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
