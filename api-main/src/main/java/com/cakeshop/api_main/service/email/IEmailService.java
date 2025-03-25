package com.cakeshop.api_main.service.email;

public interface IEmailService {

    void sendEmail(String toEmail, String subject, String body);

    boolean validateOtp(String email, String otp);

    void saveOtp(String email, String otp);

    void sendEmailAsync(String toEmail, String username, String otpCode);

}
