package com.cakeshop.api_main.utils;

import java.util.Random;

public class StringBuilderUtils {

    public static String subjectSendEmail =  "🔑 Activate Your Account at CakeShop!";

    public static String subjectChangePasswordDefault =  "🔑 Change your default password from CakeShop!";

    public static String subjectResendEmail = "🔑 Resend OTP for Your CakeShop Account";

    public static String buildBodyResendEmail(String username, String otpCode) {
        return "Hello " + username + ",\n\n"
                + "We noticed that you requested a new OTP to activate your account. Please find your new OTP code below:\n\n"
                + "🔒 Your OTP Code: " + otpCode + "\n\n"
                + "This code is valid for the next 5 minutes. Please do not share this code with anyone.\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The CakeShop Team";
    }

    public static String buildBodyChangePasswordDefault(String username, String defaultPassword) {
        return "Hello " + username + ",\n\n"
                + "We noticed that your account created with Google and we was generated default password for you. Please change this:\n\n"
                + "🔒 Your default password: " + defaultPassword + "\n\n"
                + "This password is valid for your account. Please do not share this password with anyone.\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The CakeShop Team";
    }

    public static String buildBodySendEmail(String username, String otpCode) {
        return "Hello " + username + ",\n\n"
                + "Thank you for signing up at CakeShop. To activate your account, please use the following OTP code:\n\n"
                + "🔒 Your OTP Code: " + otpCode + "\n\n"
                + "This code is valid for the next 5 minutes. Please do not share this code with anyone.\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The CakeShop Team";
    }

    public static String generateDefaultPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
