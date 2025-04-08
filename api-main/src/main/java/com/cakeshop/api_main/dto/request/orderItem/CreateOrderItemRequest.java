package com.cakeshop.api_main.dto.request.orderItem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Schema(description = "Create order item Form")
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderItemRequest {
    @Schema(description = "productId", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "productId can not be null")
    String productId;

    @Schema(description = "quantity", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "quantity can not be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    int quantity;

    @Schema(description = "note", example = "note")
    String note;
}
