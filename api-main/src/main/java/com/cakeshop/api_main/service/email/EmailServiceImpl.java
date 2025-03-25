package com.cakeshop.api_main.service.email;

import com.cakeshop.api_main.service.redis.IRedisService;
import com.cakeshop.api_main.utils.StringBuilderUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements IEmailService {

    JavaMailSender mailSender;

    IRedisService redisService;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        String redisKey = getRedisKeyForConfirmEmail(email);
        String storedOtp = redisService.getObject(redisKey, String.class);

        if (storedOtp != null && storedOtp.equals(otp)) {
            redisService.deleteObject(redisKey);
            return true;
        }
        return false;
    }

    @Override
    public void saveOtp(String email, String otp) {
        redisService.setObject(getRedisKeyForConfirmEmail(email), otp, 150);
    }

    private String getRedisKeyForConfirmEmail(String email) {
        return "auth:email:otp:" + email;
    }

    // ===== Async Email =====

    @Override
    @Async("taskExecutor")
    public void sendEmailAsync(String toEmail, String username, String otpCode) {
        try {
            String subject = StringBuilderUtils.subjectSendEmail;
            String body = StringBuilderUtils.buildBodySendEmail(username, otpCode);

            // Thực chất email sẽ được gửi ở đây
            sendEmail(toEmail, subject, body);
            log.info("Email sent to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }
}

