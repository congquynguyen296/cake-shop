package com.cakeshop.api_main.dto.response.cartItem;

import com.cakeshop.api_main.dto.response.product.ProductResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CartItemResponse {
    String id;
    ProductResponse product;
    private Integer quantity;
}