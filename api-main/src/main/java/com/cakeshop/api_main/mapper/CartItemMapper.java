package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.cartItem.CartItemResponse;
import com.cakeshop.api_main.model.CartItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class, TagMapper.class})
public interface CartItemMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductResponseAutoComplete")
    @Mapping(source = "quantity", target = "quantity")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCartItemResponse")
    CartItemResponse fromEntityToCartItemResponse(CartItem cartItem);

    @IterableMapping(elementTargetType = CartItemResponse.class, qualifiedByName = "fromEntityToCartItemResponse")
    @Named("fromEntitiesToCartItemResponseList")
    List<CartItemResponse> fromEntitiesToCartItemResponseList(List<CartItem> cartItems);
}
