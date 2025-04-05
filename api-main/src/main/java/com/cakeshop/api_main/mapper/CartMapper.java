package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.cart.CartResponse;
import com.cakeshop.api_main.model.Cart;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "fromEntitiesToCartItemResponseList")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCartResponse")
    CartResponse fromEntityToCartResponse(Cart cart);
}
