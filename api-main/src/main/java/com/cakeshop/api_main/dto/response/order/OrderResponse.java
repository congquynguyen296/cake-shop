package com.cakeshop.api_main.dto.response.order;

import com.cakeshop.api_main.dto.response.address.AddressResponse;
import com.cakeshop.api_main.dto.response.customer.CustomerResponse;
import com.cakeshop.api_main.dto.response.orderItem.OrderItemResponse;
import com.cakeshop.api_main.dto.response.orderStatus.OrderStatusResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderResponse {
    @Schema(description = "id")
    String id;
    @Schema(description = "customer")
    CustomerResponse customer;
    @Schema(description = "orderItems")
    List<OrderItemResponse> orderItems;
    @Schema(description = "shippingFee")
    Integer shippingFee;
    @Schema(description = "paymentMethod")
    Integer paymentMethod;
    @Schema(description = "totalAmount")
    Double totalAmount;
    @Schema(description = "totalDiscount")
    Double totalDiscount;
    @Schema(description = "status")
    OrderStatusResponse status;
    @Schema(description = "note")
    String note;
    @Schema(description = "address")
    AddressResponse address;
}
