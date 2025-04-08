package com.cakeshop.api_main.dto.response.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class ProductSoldResponse {
    @Schema(description = "productId")
    String productId;
    @Schema(description = "totalSold")
    Long totalSold;
}
