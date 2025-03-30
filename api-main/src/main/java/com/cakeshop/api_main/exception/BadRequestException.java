package com.cakeshop.api_main.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public BadRequestException(String message) {
        super(message);
        this.errorCode = ErrorCode.RESOURCE_EXISTED;
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage(), null, false, false); // Disable Stack trace
        this.errorCode = errorCode;
    }

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, null, false, false); // Disable Stack trace
        this.errorCode = errorCode;
    }
}
