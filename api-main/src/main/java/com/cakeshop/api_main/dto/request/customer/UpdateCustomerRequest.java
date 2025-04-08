package com.cakeshop.api_main.dto.request.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    String firstName;

    @Schema(description = "lastName", example = "Nguyen")
    String lastName;

    @Schema(description = "dob", example = "1")
    Date dob;
}
