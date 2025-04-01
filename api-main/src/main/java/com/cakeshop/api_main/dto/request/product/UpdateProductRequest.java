package com.cakeshop.api_main.dto.request.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Update Product Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductRequest {
    @Schema(description = "ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "ID cannot be null")
    private String id;

    @Schema(description = "Name", example = "Bánh", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Schema(description = "Price", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Price can not be empty")
    private Double price;

    @Schema(description = "Description", example = "mô tả", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Description can not be empty")
    private String description;

    @Schema(description = "Quantity", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Quantity can not be empty")
    private Long quantity;

    @Schema(description = "images")
    @NotNull(message = "images can not be null")
    private List<String> images;

    @Schema(description = "Status", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Status can not be empty")
    private Integer status;

    @Schema(description = "Category", example = "abc", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Category can not be empty")
    private String categoryId;

    @Schema(description = "Tags", example = "abc", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> tagIds;
}
