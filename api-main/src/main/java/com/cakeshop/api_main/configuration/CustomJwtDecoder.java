package com.cakeshop.api_main.configuration;

import com.cakeshop.api_main.dto.request.IntrospectRequest;
import com.cakeshop.api_main.exception.AppException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.service.authentication.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;


@Component
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Autowired
    private IAuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder;

    @Override
    public Jwt decode(String token) throws JwtException {

        var response = authenticationService.introspect(
                IntrospectRequest.builder().token(token).build());
        log.error(ErrorCode.UNAUTHENTICATED.getMessage());

        if (!response.isValid()) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}