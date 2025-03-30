package com.cakeshop.api_main.dto.request.nation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Create Nation Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateNationRequest {
    @Schema(description = "name", example = "Hồ Chí Minh", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "name can not be empty")
    private String name;

    @Schema(description = "kind", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "kind can not be null")
    private Integer kind;

    @Schema(description = "parentId", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "parentId can not be null")
    private String parentId;
}
