package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.address.AddressResponse;
import com.cakeshop.api_main.model.Address;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NationMapper.class})
public interface AddressMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "province", target = "province", qualifiedByName = "fromEntityToNationResponseAutoComplete")
    @Mapping(source = "district", target = "district", qualifiedByName = "fromEntityToNationResponseAutoComplete")
    @Mapping(source = "commune", target = "commune", qualifiedByName = "fromEntityToNationResponseAutoComplete")
    @Mapping(source = "details", target = "details")
    @Mapping(source = "isDefault", target = "isDefault")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAddressResponse")
    AddressResponse fromEntityToAddressResponse(Address address);

    @IterableMapping(elementTargetType = AddressResponse.class, qualifiedByName = "fromEntityToAddressResponse")
    List<AddressResponse> fromEntitiesToAddressResponseList(List<Address> addresses);
}
