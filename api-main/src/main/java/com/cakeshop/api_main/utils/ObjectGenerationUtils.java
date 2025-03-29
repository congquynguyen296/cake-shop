package com.cakeshop.api_main.utils;

import com.cakeshop.api_main.exception.AppException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.model.Account;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Component // Đánh dấu class là Spring Bean
public class ObjectGenerationUtils {

    private static final Random RANDOM = new Random();

    @NonFinal
    @Value("${jwt.valid-duration}")
    private long validDuration;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    private long refreshableDuration;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;

    public String generateOtp() {
        return String.format("%06d", RANDOM.nextInt(999999));
    }

    public String generateToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("SystemAdmin")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(validDuration, ChronoUnit.SECONDS))) // Sử dụng biến instance
                .claim("scope", buildScope(account))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes())); // Sử dụng biến instance
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        var permissions = account.getGroup().getPermissions();

        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(permission -> {
                stringJoiner.add(permission.getCode());
            });
        }

        return stringJoiner.toString();
    }
}