package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.orderItem.OrderItemResponse;
import com.cakeshop.api_main.model.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerMapper.class, ProductMapper.class})
public interface OrderItemMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductResponseAutoComplete")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "unitPrice", target = "price")
    @Mapping(source = "unitDiscountPercentage", target = "discountPercentage")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOrderItemResponse")
    OrderItemResponse fromEntityToOrderItemResponse(OrderItem orderItem);

    @IterableMapping(elementTargetType = OrderItemResponse.class, qualifiedByName = "fromEntityToOrderItemResponse")
    @Named("fromEntitiesToOrderItemResponseList")
    List<OrderItemResponse> fromEntitiesToOrderItemResponseList(List<OrderItem> orderItems);
}
