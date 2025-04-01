package com.cakeshop.api_main.dto.request.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Update Category Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCategoryRequest {
    @Schema(description = "ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID cannot be null")
    private String id;

    @Schema(description = "Code", example = "BIRTHDAY_CAKE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Code can not be empty")
    private String code;

    @Schema(description = "Name", example = "Bánh Sinh Nhật", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Schema(description = "Description", example = "Những chiếc bánh sinh nhật ngọt ngào với nhiều hương vị hấp dẫn.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Description can not be empty")
    private String description;

    @Schema(description = "Image", example = "/image", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Image can not be empty")
    private String image;
}
