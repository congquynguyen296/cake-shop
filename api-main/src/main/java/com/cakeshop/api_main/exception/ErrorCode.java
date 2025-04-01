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
    DELETE_RELATIONSHIP_ERROR(1000, "Delete Relationship Error", HttpStatus.BAD_REQUEST),;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
