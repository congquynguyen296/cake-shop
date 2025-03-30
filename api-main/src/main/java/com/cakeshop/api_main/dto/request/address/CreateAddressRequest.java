package com.cakeshop.api_main.dto.request.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Create Address Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAddressRequest {
    @Schema(description = "provinceId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "provinceId can not be empty")
    private String provinceId;

    @Schema(description = "districtId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "districtId can not be empty")
    private String districtId;

    @Schema(description = "communeId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "communeId can not be empty")
    private String communeId;

    @Schema(description = "details")
    private String details;

    @Schema(description = "isDefault", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "isDefault can not be null")
    private Boolean isDefault;
}
