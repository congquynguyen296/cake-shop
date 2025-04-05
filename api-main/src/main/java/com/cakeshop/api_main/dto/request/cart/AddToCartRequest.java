package com.cakeshop.api_main.dto.request.cart;

import com.cakeshop.api_main.dto.request.cartItem.CreateCartItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddToCartRequest {
    @Schema(description = "cartItems", example = "[productId: 1, quantity: 1]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "cartItems can not be null")
    List<CreateCartItemRequest> cartItems;
}
