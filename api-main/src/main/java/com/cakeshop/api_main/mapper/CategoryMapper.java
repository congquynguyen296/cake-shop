package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.request.category.CreateCategoryRequest;
import com.cakeshop.api_main.dto.request.category.UpdateCategoryRequest;
import com.cakeshop.api_main.dto.response.category.CategoryResponse;
import com.cakeshop.api_main.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateCategoryRequest")
    Category fromCreateCategoryRequest(CreateCategoryRequest request);

    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateFromUpdateCategoryRequest")
    void updateFromUpdateCategoryRequest(@MappingTarget Category category, UpdateCategoryRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCategoryResponse")
    CategoryResponse fromEntityToCategoryResponse(Category category);

    @IterableMapping(elementTargetType = CategoryResponse.class, qualifiedByName = "fromEntityToCategoryResponse")
    List<CategoryResponse> fromEntitiesToCategoryResponseList(List<Category> categories);
}
