package com.cakeshop.api_main.dto.request.cart;

import com.cakeshop.api_main.dto.request.cartItem.CreateCartItemRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Schema(description = "Add to cart Form")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AddToCartRequest {
    @Schema(description = "cartItems", example = "[productId: 1, quantity: 1]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "cartItems can not be null")
    List<CreateCartItemRequest> cartItems;
}
