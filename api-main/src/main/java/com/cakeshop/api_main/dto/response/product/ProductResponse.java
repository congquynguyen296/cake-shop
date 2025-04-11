package com.cakeshop.api_main.dto.response.product;

import com.cakeshop.api_main.dto.response.category.CategoryResponse;
import com.cakeshop.api_main.dto.response.discount.DiscountResponse;
import com.cakeshop.api_main.dto.response.review.ReviewStatsResponse;
import com.cakeshop.api_main.dto.response.tag.TagResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "name")
    String name;

    @Schema(description = "price")
    Double price;

    @Schema(description = "description")
    String description;

    @Schema(description = "quantity")
    Long quantity;

    @Schema(description = "category")
    CategoryResponse category;

    @Schema(description = "tags")
    List<TagResponse> tags;

    @Schema(description = "discount")
    DiscountResponse discount;

    @Schema(description = "status")
    Integer status;

    @Schema(description = "image")
    String image;

    @Schema(description = "images")
    List<String> images;

    @Schema(description = "Total quantity sold")
    Long totalSold;

    @Schema(description = "reviewStats")
    ReviewStatsResponse reviewStats;
}
