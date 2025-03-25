package com.cakeshop.api_main.service.authentication;

import com.cakeshop.api_main.dto.request.*;
import com.cakeshop.api_main.dto.response.IntrospectResponse;
import com.cakeshop.api_main.dto.response.LoginResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {

    LoginResponse login(LoginRequest request);

    String logout(LogoutRequest request);

    String register(RegisterRequest request);

    LoginResponse refresh(RefreshTokenRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request);

    String activeAccount(ActiveAccountRequest request);

    String resendOtpCode(ResendOtpCodeRequest request);

    LoginResponse loginWithGoogle(String code);
}
