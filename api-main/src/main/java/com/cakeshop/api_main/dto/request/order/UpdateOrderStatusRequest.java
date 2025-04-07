package com.cakeshop.api_main.dto.request.order;

import com.cakeshop.api_main.validation.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderStatusRequest {
    @Schema(description = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id can not be null")
    String id;

    @Schema(description = "paymentMethod", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @OrderStatus
    Integer status;
}
