package com.cakeshop.api_main.dto.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    String image;
}
