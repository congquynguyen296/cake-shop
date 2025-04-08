package com.cakeshop.api_main.dto.request.order;

import com.cakeshop.api_main.dto.request.orderItem.CreateOrderItemRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Schema(description = "Create order Form")
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    @Schema(description = "orderItems", example = "[productId: 1, quantity: 1]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "orderItems can not be null")
    List<CreateOrderItemRequest> orderItems;

    @Schema(description = "shippingFee", example = "30000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "shippingFee can not be null")
    Integer shippingFee;

    @Schema(description = "paymentMethod", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "paymentMethod can not be null")
    Integer paymentMethod;
}
