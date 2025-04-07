package com.cakeshop.api_main.dto.response.customer;

import com.cakeshop.api_main.dto.response.address.AddressResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

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
    @Schema(description = "dob")
    Date dob;
    @Schema(description = "loyalty")
    Long loyalty;
    @Schema(description = "addresses")
    List<AddressResponse> addresses;
}
