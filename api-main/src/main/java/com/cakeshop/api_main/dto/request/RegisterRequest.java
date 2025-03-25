package com.cakeshop.api_main.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegisterRequest {

    String username;

    String password;

    String confirmPassword;

    String email;

    boolean termsAccepted;
}
