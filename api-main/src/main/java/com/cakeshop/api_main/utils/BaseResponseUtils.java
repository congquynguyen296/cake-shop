package com.cakeshop.api_main.utils;


import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.exception.ErrorCode;

/**
 * Utility class for generating API response messages.
 */
public final class BaseResponseUtils {

    private BaseResponseUtils() {
        // Prevent instantiation
    }

    /**
     * Base method to create an API response.
     */
    private static <T> BaseResponse<T> baseResponse(boolean result, int code, T data, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setResult(result);
        response.setCode(code);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    /**
     * Generates a successful API response.
     */
    public static <T> BaseResponse<T> success(T data, String message) {
        return baseResponse(true, 200, data, message);
    }

    /**
     * Generates an error API response based on ErrorCode.
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return baseResponse(false, errorCode.getCode(), null, errorCode.getMessage());
    }
}
