package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.request.nation.CreateNationRequest;
import com.cakeshop.api_main.dto.request.nation.UpdateNationRequest;
import com.cakeshop.api_main.dto.response.nation.NationResponse;
import com.cakeshop.api_main.model.Nation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NationMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateNationRequest")
    Nation fromCreateNationRequest(CreateNationRequest request);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateFromUpdateNationRequest")
    void updateFromUpdateNationRequest(@MappingTarget Nation nation, UpdateNationRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "parent.id", target = "parentId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNationResponse")
    NationResponse fromEntityToNationResponse(Nation nation);

    @IterableMapping(elementTargetType = NationResponse.class, qualifiedByName = "fromEntityToNationResponse")
    List<NationResponse> fromEntitiesToNationResponseList(List<Nation> nations);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNationResponseAutoComplete")
    NationResponse fromEntityToNationResponseAutoComplete(Nation nation);
}
