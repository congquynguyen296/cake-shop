package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.orderStatus.OrderStatusResponse;
import com.cakeshop.api_main.model.OrderStatus;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderStatusMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "date", target = "date")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOrderStatusResponse")
    OrderStatusResponse fromEntityToOrderStatusResponse(OrderStatus orderStatus);

    @IterableMapping(elementTargetType = OrderStatusResponse.class, qualifiedByName = "fromEntityToOrderStatusResponse")
    @Named("fromEntitiesToOrderStatusResponseList")
    List<OrderStatusResponse> fromEntitiesToOrderStatusResponseList(List<OrderStatus> orderStatuses);
}
