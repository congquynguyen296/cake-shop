package com.cakeshop.api_main.dto.response.nation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class NationResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "name")
    String name;

    @Schema(description = "kind")
    Integer kind;

    @Schema(description = "parentId")
    String parentId;
}
