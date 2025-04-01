package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.*;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.LoginResponse;
import com.cakeshop.api_main.service.authentication.IAuthenticationService;
import com.cakeshop.api_main.utils.BaseResponseUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationController {

    IAuthenticationService authenticationService;

    @PostMapping("/login")
    BaseResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return BaseResponseUtils.success(authenticationService.login(request), "Login successful");
    }

    @PostMapping("/refresh-token")
    BaseResponse<LoginResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) throws ParseException, JOSEException {
        return BaseResponseUtils.success(authenticationService.refresh(request), "Refresh token successful");
    }

    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody @Valid RegisterRequest request) {
        return BaseResponseUtils.success(null, authenticationService.register(request));
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(@RequestBody @Valid LogoutRequest request) {
        return BaseResponseUtils.success(null, authenticationService.logout(request));
    }

    @PostMapping("/active-account")
    public BaseResponse<Void> activeAccount(@RequestBody @Valid ActiveAccountRequest request) {
        return BaseResponseUtils.success(null, authenticationService.activeAccount(request));
    }

    @PostMapping("/resend-otp-code")
    public BaseResponse<Void> resendOtpCode(@RequestBody @Valid ResendOtpCodeRequest request) {
        return BaseResponseUtils.success(null, authenticationService.resendOtpCode(request));
    }

}
