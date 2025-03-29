package com.cakeshop.api_main.dto.response.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class TagResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "code")
    String code;

    @Schema(description = "name")
    String name;
}
