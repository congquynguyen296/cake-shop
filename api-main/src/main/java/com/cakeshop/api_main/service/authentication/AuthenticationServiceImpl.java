package com.cakeshop.api_main.service.authentication;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.dto.request.*;
import com.cakeshop.api_main.dto.response.ExchangeTokenResponse;
import com.cakeshop.api_main.dto.response.IntrospectResponse;
import com.cakeshop.api_main.dto.response.LoginResponse;
import com.cakeshop.api_main.dto.response.OutboundUserResponse;
import com.cakeshop.api_main.exception.AppException;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.model.Account;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.model.Group;
import com.cakeshop.api_main.model.TokenValidation;
import com.cakeshop.api_main.repository.external.IOutboundIdentityClientRepository;
import com.cakeshop.api_main.repository.external.IOutboundUserClientRepository;
import com.cakeshop.api_main.repository.internal.IAccountRepository;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.repository.internal.IGroupRepository;
import com.cakeshop.api_main.repository.internal.ITokenValidationRepository;
import com.cakeshop.api_main.service.email.IEmailService;
import com.cakeshop.api_main.utils.ObjectGenerationUtils;
import com.cakeshop.api_main.utils.StringBuilderUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {

    IAccountRepository accountRepository;
    ITokenValidationRepository tokenValidationRepository;
    IGroupRepository groupRepository;
    ICustomerRepository customerRepository;
    IEmailService emailService;
    PasswordEncoder passwordEncoder;
    ObjectGenerationUtils objectGenerationUtils;

    // From outbound oauth2
    IOutboundUserClientRepository outboundUserClientRepository;
    IOutboundIdentityClientRepository outboundIdentityClientRepository;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    @Override
    public LoginResponse login(LoginRequest request) {

        Account existedAccount = accountRepository.findByUsername(request.getUsername());

        if (existedAccount == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }


        boolean authenticated = passwordEncoder.matches(request.getPassword(), existedAccount.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Generate token when authenticate success
        String token = objectGenerationUtils.generateToken(existedAccount);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public String logout(LogoutRequest request) {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);

            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();

            TokenValidation token = TokenValidation.builder()
                    .id(jit)
                    .expiryDate(exp)
                    .build();
            tokenValidationRepository.save(token);

            return "Login is successful";

        } catch (AppException | JOSEException | ParseException e) {
            log.info("Token hết hiệu lực");
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String register(RegisterRequest request) {
        if (accountRepository.existsByEmail(request.getEmail()) ||
                accountRepository.existsByUsername(request.getUsername())) {
            log.info("Email or Username existed");
            throw new BadRequestException(ErrorCode.RESOURCE_EXISTED);
        }

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            log.info("Passwords do not match");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        try {
            Account newAccount = Account.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .avatarPath("https://res.cloudinary.com/dcxgx3ott/image/upload/v1744349601/Avatar_UTE_hddqyo.png")
                    .password(passwordEncoder.encode(request.getPassword()))
                    .isActive(false)
                    .build();

            // Default group
            Group defaultGroup = groupRepository.findByKind(BaseConstant.GROUP_KIND_CUSTOMER);
            if (defaultGroup == null) {
                throw new NotFoundException(ErrorCode.GROUP_NOT_FOUND_ERROR);
            }
            newAccount.setGroup(defaultGroup);

            var accountSaved = accountRepository.save(newAccount);

            String otpCode = objectGenerationUtils.generateOtp();
            emailService.saveOtp(accountSaved.getEmail(), otpCode);

            // Send otp qua email ASYNC
            emailService.sendEmailAsync(accountSaved.getEmail(), accountSaved.getUsername(), otpCode);

            return "Đăng ký thành công! Vui lòng xác minh email của bạn trong vòng 5 " +
                    "phút để kích hoạt tài khoản.";

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LoginResponse refresh(RefreshTokenRequest request) throws ParseException, JOSEException {

        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        TokenValidation invalidated = TokenValidation.builder()
                .id(jit)
                .expiryDate(expiryDate)
                .build();
        try {
            tokenValidationRepository.save(invalidated);

            var existedAccount = accountRepository.findByUsername(signedJWT.getJWTClaimsSet().getSubject());
            if (existedAccount == null) {
                log.info("User not found");
                throw new AppException(ErrorCode.RESOURCE_NOT_EXISTED);
            }

            var newToken = objectGenerationUtils.generateToken(existedAccount);
            return LoginResponse.builder()
                    .token(newToken)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            var signed = verifyToken(token, false);
        } catch (AppException | JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public String activeAccount(ActiveAccountRequest request) {

        boolean isValidOtp = emailService.validateOtp(request.getEmail(), request.getOtpCode());
        if (!isValidOtp) {
            log.error("OTP code is not match or invalid");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        try {

            Account existedAccount = accountRepository.findByEmail(request.getEmail());
            if (existedAccount == null) {
                log.info("Account not found");
                throw new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND_ERROR);
            }
            existedAccount.setIsActive(true);

            Customer customer = Customer.builder()
                    .account(existedAccount)
                    .firstName("")
                    .lastName("")
                    .dob(null)
                    .loyalty(0L)
                    .addresses(new ArrayList<>())
                    .build();

            accountRepository.save(existedAccount);
            customerRepository.save(customer);

            return "Active account is successful";

        } catch (Exception e) {
            log.error("Error in validation OTP code: {}", e.getMessage());
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String resendOtpCode(ResendOtpCodeRequest request) {

        Account existedAccount = accountRepository.findByEmail(request.getEmail());
        if (existedAccount == null) {
            log.info("Account not found");
            throw new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND_ERROR);
        }

        String message = "";

        if (!existedAccount.getIsActive()) {
            String newOtpCode = objectGenerationUtils.generateOtp();
            emailService.saveOtp(existedAccount.getEmail(), newOtpCode);

            String subject = StringBuilderUtils.subjectResendEmail;
            String body = StringBuilderUtils.buildBodyResendEmail(existedAccount.getUsername(), newOtpCode);

            emailService.sendEmail(existedAccount.getEmail(), subject, body);
            message = "Resend email successful";
        } else {
            message = "User is already active";
        }
        return message;
    }

    @Override
    public LoginResponse loginWithGoogle(String code) {

        try {
            ExchangeTokenRequest exchangeToken = ExchangeTokenRequest.builder()
                    .code(code)
                    .clientId(CLIENT_ID)
                    .clientSecret(CLIENT_SECRET)
                    .grantType(GRANT_TYPE)
                    .redirectUri(REDIRECT_URI)
                    .build();
            ExchangeTokenResponse response = outboundIdentityClientRepository.exchangeToken(exchangeToken);
            log.info("Token response from credential: {}", response);

            // Get user info
            OutboundUserResponse accountInfoFromGoogle = outboundUserClientRepository.getUserInfo("json", response.getAccessToken());
            log.info("Account info from google: {}", accountInfoFromGoogle);

            // Onboard account with system
            Account account = accountRepository.findByEmail(accountInfoFromGoogle.getEmail());
            if (account == null) {
                String defaultPassword = StringBuilderUtils.generateDefaultPassword();
                emailService.sendEmail(accountInfoFromGoogle.getEmail(), StringBuilderUtils.subjectChangePasswordDefault
                        , StringBuilderUtils.buildBodyChangePasswordDefault(accountInfoFromGoogle.getEmail(), defaultPassword));
                account = Account.builder()
                        .isActive(true)
                        .username(accountInfoFromGoogle.getEmail())
                        .email(accountInfoFromGoogle.getEmail())
                        .avatarPath(accountInfoFromGoogle.getPicture())
                        .password(passwordEncoder.encode(defaultPassword))
                        .build();
                Group defaultGroup = groupRepository.findByKind(BaseConstant.GROUP_KIND_CUSTOMER);
                if (defaultGroup == null) {
                    throw new NotFoundException(ErrorCode.GROUP_NOT_FOUND_ERROR);
                }
                account.setGroup(defaultGroup);
                accountRepository.save(account);
            }

            // Generate token
            String token = objectGenerationUtils.generateToken(account);
                return LoginResponse.builder()
                    .token(token)
                    .build();

        } catch (Exception e) {
            log.error("Login with google failed: {}", e.getMessage());
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // Verify token
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            log.error("Invalid or timeout JWT code");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        };

        // Thực hiện logic kiểm tra token tại đây
        if (tokenValidationRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            log.error("JWT is used before expiry");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
