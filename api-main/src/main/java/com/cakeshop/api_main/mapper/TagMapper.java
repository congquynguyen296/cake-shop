package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.request.tag.CreateTagRequest;
import com.cakeshop.api_main.dto.request.tag.UpdateTagRequest;
import com.cakeshop.api_main.dto.response.tag.TagResponse;
import com.cakeshop.api_main.model.Tag;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateTagRequest")
    Tag fromCreateTagRequest(CreateTagRequest request);

    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateFromUpdateTagRequest")
    void updateFromUpdateTagRequest(@MappingTarget Tag tag, UpdateTagRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTagResponse")
    TagResponse fromEntityToTagResponse(Tag tag);

    @IterableMapping(elementTargetType = TagResponse.class, qualifiedByName = "fromEntityToTagResponse")
    @Named("fromEntitiesToTagResponseList")
    List<TagResponse> fromEntitiesToTagResponseList(List<Tag> tags);
}
