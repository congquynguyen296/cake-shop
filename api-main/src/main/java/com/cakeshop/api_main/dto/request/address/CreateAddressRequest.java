package com.cakeshop.api_main.dto.request.address;

import com.cakeshop.api_main.validation.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Schema(description = "Create Address Form")
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressRequest {
    @Schema(description = "provinceId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "provinceId can not be empty")
    String provinceId;

    @Schema(description = "districtId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "districtId can not be empty")
    String districtId;

    @Schema(description = "communeId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "communeId can not be empty")
    String communeId;

    @Schema(description = "details")
    String details;

    @Schema(description = "isDefault", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "isDefault can not be null")
    Boolean isDefault;

    @Schema(description = "fullName", example = "Nguyễn Văn A")
    @NotBlank(message = "fullName cannot be empty")
    String fullName;

    @Schema(description = "phoneNumber", example = "0327450088")
    @PhoneNumber
    String phoneNumber;
}
