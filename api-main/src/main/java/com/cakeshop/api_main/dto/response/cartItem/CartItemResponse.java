package com.cakeshop.api_main.dto.response.cartItem;

import com.cakeshop.api_main.dto.response.product.ProductResponse;
import com.cakeshop.api_main.dto.response.tag.TagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CartItemResponse {
    @Schema(description = "id")
    String id;
    @Schema(description = "product")
    ProductResponse product;
    @Schema(description = "tag")
    TagResponse tag;
    @Schema(description = "quantity")
    Integer quantity;
}