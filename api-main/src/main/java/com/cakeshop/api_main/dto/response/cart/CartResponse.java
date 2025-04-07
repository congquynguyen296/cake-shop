package com.cakeshop.api_main.dto.response.cart;

import com.cakeshop.api_main.dto.response.cartItem.CartItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CartResponse {
    @Schema(description = "id")
    String id;
    @Schema(description = "cartItems")
    List<CartItemResponse> cartItems;
}
