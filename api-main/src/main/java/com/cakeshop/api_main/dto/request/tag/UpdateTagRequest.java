package com.cakeshop.api_main.dto.request.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Update Tag Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTagRequest {
    @Schema(description = "ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "ID cannot be null")
    private String id;

    @Schema(description = "Code", example = "CHOCOLATE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Code can not be empty")
    private String code;

    @Schema(description = "Name", example = "Socola", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name can not be empty")
    private String name;
}
