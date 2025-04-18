package com.cakeshop.api_main.dto.request.customer;

import com.cakeshop.api_main.validation.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Schema(description = "Update Customer Form")
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerRequest {
    @Schema(description = "ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID cannot be null")
    String id;

    @Schema(description = "firstName", example = "John")
    @NotBlank(message = "firstName cannot be empty")
    String firstName;

    @Schema(description = "lastName", example = "Nguyen")
    @NotBlank(message = "lastName cannot be empty")
    String lastName;

    @Schema(description = "fullName", example = "Nguyễn Văn A")
    @NotBlank(message = "fullName cannot be empty")
    String fullName;

    @Schema(description = "phoneNumber", example = "0327450088")
    @PhoneNumber
    String phoneNumber;

    @Schema(description = "dob", example = "1")
    @NotNull(message = "dob cannot be null")
    Date dob;
}
