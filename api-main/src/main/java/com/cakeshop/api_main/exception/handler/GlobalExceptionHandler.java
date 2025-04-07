package com.cakeshop.api_main.exception.handler;

import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Error - No Handler Found
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>(
                false,
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                null,
                "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<List<HashMap<String, String>>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<HashMap<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    HashMap<String, String> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("message", fieldError.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList());

        BaseResponse<List<HashMap<String, String>>> response = new BaseResponse<>(
                false,
                ErrorCode.INVALID_FORM_ERROR.getCode(),
                ErrorCode.INVALID_FORM_ERROR.getMessage(),
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleResourceNotFoundException(NotFoundException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        BaseResponse<Void> response = new BaseResponse<>(
                false,
                errorCode.getCode(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Void>> handleResourceBadRequestException(BadRequestException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        BaseResponse<Void> response = new BaseResponse<>(
                false,
                errorCode.getCode(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(BaseResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    // Other Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGlobalException(Exception ex) {
        BaseResponse<Void> response = new BaseResponse<>(
                false,
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}