package com.cakeshop.api_main.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.RESOURCE_NOT_EXISTED;
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage(), null, false, false); // Disable Stack trace
        this.errorCode = errorCode;
    }

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, null, false, false); // Disable Stack trace
        this.errorCode = errorCode;
    }
}
