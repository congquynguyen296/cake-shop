package com.cakeshop.api_main.mapper;

import com.cakeshop.api_main.dto.request.product.CreateProductRequest;
import com.cakeshop.api_main.dto.request.product.UpdateProductRequest;
import com.cakeshop.api_main.dto.response.product.ProductResponse;
import com.cakeshop.api_main.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class, TagMapper.class, DiscountMapper.class})
public interface ProductMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quantity", target = "quantity")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateProductRequest")
    Product fromCreateProductRequest(CreateProductRequest request);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateFromUpdateProductRequest")
    void updateFromUpdateProductRequest(@MappingTarget Product product, UpdateProductRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryResponseAutoComplete")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "fromEntityToTagResponse")
    @Mapping(source = "discount", target = "discount", qualifiedByName = "fromEntityToDiscountResponse")
    @Mapping(source = "images", target = "images")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductResponse")
    ProductResponse fromEntityToProductResponse(Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryResponseAutoComplete")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "fromEntityToTagResponse")
    @Mapping(source = "discount", target = "discount", qualifiedByName = "fromEntityToDiscountResponse")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductResponseDetails")
    ProductResponse fromEntityToProductResponseDetails(Product product);

    @IterableMapping(elementTargetType = ProductResponse.class, qualifiedByName = "fromEntityToProductResponse")
    List<ProductResponse> fromEntitiesToProductResponseList(List<Product> products);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "images", target = "image", qualifiedByName = "mapFirstImage")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryResponseAutoComplete")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "fromEntityToTagResponse")
    @Mapping(source = "discount", target = "discount", qualifiedByName = "fromEntityToDiscountResponse")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductResponseAutoComplete")
    ProductResponse fromEntityToProductResponseAutoComplete(Product product);

    @Named("mapFirstImage")
    default String mapFirstImage(List<String> images) {
        return (images != null && !images.isEmpty()) ? images.getFirst() : null;
    }
}
