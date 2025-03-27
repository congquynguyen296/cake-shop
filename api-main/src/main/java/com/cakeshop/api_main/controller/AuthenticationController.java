package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.*;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.LoginResponse;
import com.cakeshop.api_main.service.authentication.IAuthenticationService;
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
        return BaseResponse.<LoginResponse>builder()
                .result(true)
                .code(200)
                .message("Login successful")
                .data(authenticationService.login(request))
                .build();
    }

    @PostMapping("/refresh-token")
    BaseResponse<LoginResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) throws ParseException, JOSEException {
        return BaseResponse.<LoginResponse>builder()
                .result(true)
                .code(200)
                .message("Refresh token successful")
                .data(authenticationService.refresh(request))
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        String message = authenticationService.register(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody @Valid LogoutRequest request) {
        String message = authenticationService.logout(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/active-account")
    public ResponseEntity<String> activeAccount(@RequestBody @Valid ActiveAccountRequest request) {
        String message = authenticationService.activeAccount(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/resend-otp-code")
    public ResponseEntity<String> resendOtpCode(@RequestBody @Valid ResendOtpCodeRequest request) {
        String message = authenticationService.resendOtpCode(request);
        return ResponseEntity.ok(message);
    }

}
