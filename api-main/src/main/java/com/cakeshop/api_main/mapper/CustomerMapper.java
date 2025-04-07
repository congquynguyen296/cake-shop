package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.response.customer.CustomerResponse;
import com.cakeshop.api_main.model.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AddressMapper.class})
public interface CustomerMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.avatarPath", target = "avatarPath")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "dob", target = "dob")
    @Mapping(source = "loyalty", target = "loyalty")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCustomerResponse")
    CustomerResponse fromEntityToCustomerResponse(Customer customer);
}
