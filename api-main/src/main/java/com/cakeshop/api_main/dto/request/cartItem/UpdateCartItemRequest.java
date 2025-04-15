package com.cakeshop.api_main.dto.request.cartItem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Update cart item Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCartItemRequest {
    @Schema(description = "cartItemId", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "cartItemId can not be null")
    String cartItemId;

    @Schema(description = "quantity", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "quantity can not be null")
    @Min(value = 0, message = "Quantity must be greater than 0")
    int quantity;
}
