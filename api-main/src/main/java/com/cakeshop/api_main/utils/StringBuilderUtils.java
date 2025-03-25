package com.cakeshop.api_main.utils;

public class StringBuilderUtils {

    public static String subjectSendEmail =  "🔑 Activate Your Account at CakeShop!";

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

    public static String buildBodySendEmail(String username, String otpCode) {
        return "Hello " + username + ",\n\n"
                + "Thank you for signing up at CakeShop. To activate your account, please use the following OTP code:\n\n"
                + "🔒 Your OTP Code: " + otpCode + "\n\n"
                + "This code is valid for the next 5 minutes. Please do not share this code with anyone.\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The CakeShop Team";
    }
}
