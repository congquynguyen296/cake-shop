package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.LoginResponse;
import com.cakeshop.api_main.service.authentication.IAuthenticationService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutboundController {

    IAuthenticationService authenticationService;

    @GetMapping("/oauth2/login/google/callback")
    public BaseResponse<LoginResponse> outboundAuthenticate(@RequestParam("code") String code) {
        return BaseResponse.<LoginResponse>builder()
                .result(true)
                .code(200)
                .data(authenticationService.loginWithGoogle(code))
                .build();
    }
}
