package com.cakeshop.api_main.dto.response.orderItem;

import com.cakeshop.api_main.dto.response.product.ProductResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderItemResponse {
    @Schema(description = "id")
    String id;
    @Schema(description = "product")
    ProductResponse product;
    @Schema(description = "quantity")
    Integer quantity;
    @Schema(description = "price")
    Double price;
    @Schema(description = "discountPercentage")
    Integer discountPercentage;
    @Schema(description = "totalPrice")
    Double totalPrice;
}
