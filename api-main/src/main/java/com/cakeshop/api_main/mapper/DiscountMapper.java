package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.discount.DiscountResponse;
import com.cakeshop.api_main.model.Discount;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiscountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "discountPercentage", target = "discountPercentage")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDiscountResponse")
    DiscountResponse fromEntityToDiscountResponse(Discount discount);

    @IterableMapping(elementTargetType = DiscountResponse.class, qualifiedByName = "fromEntityToDiscountResponse")
    List<DiscountResponse> fromEntitiesToProductResponseList(List<Discount> discounts);
}
