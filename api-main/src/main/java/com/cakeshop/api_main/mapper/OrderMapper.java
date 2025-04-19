package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.order.OrderResponse;
import com.cakeshop.api_main.model.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerMapper.class, OrderItemMapper.class, OrderStatusMapper.class, AddressMapper.class})
public interface OrderMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "customer", target = "customer", qualifiedByName = "fromEntityToCustomerResponse")
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "fromEntitiesToOrderItemResponseList")
    @Mapping(source = "shippingFee", target = "shippingFee")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "totalDiscount", target = "totalDiscount")
    @Mapping(source = "currentStatus", target = "status", qualifiedByName = "fromEntityToOrderStatusResponse")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "address", target = "address", qualifiedByName = "fromEntityToAddressResponse")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOrderResponse")
    OrderResponse fromEntityToOrderResponse(Order order);

    @IterableMapping(elementTargetType = OrderResponse.class, qualifiedByName = "fromEntityToOrderResponse")
    List<OrderResponse> fromEntitiesToOrderResponseList(List<Order> order);
}
