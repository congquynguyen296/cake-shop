package com.cakeshop.api_main.dto.response.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "code")
    String code;

    @Schema(description = "name")
    String name;

    @Schema(description = "description")
    String description;

    @Schema(description = "image")
    private String image;
}
