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

    String code;

    String name;

    String description;

    private String image;
}
