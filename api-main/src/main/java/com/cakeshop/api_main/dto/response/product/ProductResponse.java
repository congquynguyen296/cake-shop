package com.cakeshop.api_main.dto.response.product;

import com.cakeshop.api_main.dto.response.discount.DiscountResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "name")
    String name;

    @Schema(description = "price")
    Double price;

    @Schema(description = "description")
    String description;

    @Schema(description = "quantity")
    Long quantity;

    @Schema(description = "discount")
    DiscountResponse discount;

    @Schema(description = "status")
    Integer status;
}
