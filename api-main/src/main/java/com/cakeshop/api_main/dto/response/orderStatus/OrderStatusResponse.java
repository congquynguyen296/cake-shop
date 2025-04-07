package com.cakeshop.api_main.dto.response.orderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderStatusResponse {
    @Schema(description = "id")
    String id;
    @Schema(description = "status")
    Integer status;
    @Schema(description = "date")
    Date date;
}
