package com.cakeshop.api_main.dto.response.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CustomerResponse {
    @Schema(description = "id")
    String id;
    @Schema(description = "username")
    String username;
    @Schema(description = "email")
    String email;
    @Schema(description = "avatarPath")
    String avatarPath;
    @Schema(description = "firstName")
    String firstName;
    @Schema(description = "lastName")
    String lastName;
    @Schema(description = "fullName")
    String fullName;
    @Schema(description = "dob")
    Date dob;
    @Schema(description = "phoneNumber")
    String phoneNumber;
    @Schema(description = "loyalty")
    Long loyalty;
}
