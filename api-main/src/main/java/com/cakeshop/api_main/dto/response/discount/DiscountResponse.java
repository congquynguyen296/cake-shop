package com.cakeshop.api_main.dto.response.discount;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class DiscountResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "code")
    String code;

    @Schema(description = "discountPercentage")
    Integer discountPercentage;
}
