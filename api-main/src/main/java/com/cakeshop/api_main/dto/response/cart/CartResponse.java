package com.cakeshop.api_main.dto.response.cart;

import com.cakeshop.api_main.dto.response.cartItem.CartItemResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CartResponse {
    String id;
    List<CartItemResponse> cartItems;
}
