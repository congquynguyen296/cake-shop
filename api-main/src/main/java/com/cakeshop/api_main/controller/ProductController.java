package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.dto.request.product.CreateProductRequest;
import com.cakeshop.api_main.dto.request.product.UpdateProductRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.product.ProductResponse;
import com.cakeshop.api_main.dto.response.product.ProductReviewResponse;
import com.cakeshop.api_main.dto.response.product.ProductSoldResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.ProductMapper;
import com.cakeshop.api_main.model.Category;
import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.model.Tag;
import com.cakeshop.api_main.model.criteria.ProductCriteria;
import com.cakeshop.api_main.repository.internal.ICategoryRepository;
import com.cakeshop.api_main.repository.internal.IOrderItemRepository;
import com.cakeshop.api_main.repository.internal.IProductRepository;
import com.cakeshop.api_main.repository.internal.ITagRepository;
import com.cakeshop.api_main.utils.BaseResponseUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductController {
    IProductRepository productRepository;
    ICategoryRepository categoryRepository;
    ITagRepository tagRepository;
    IOrderItemRepository orderItemRepository;

    ProductMapper productMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<ProductResponse>> list(
            @Valid @ModelAttribute ProductCriteria criteria,
            Pageable pageable
    ) {
        Page<Product> pageData = productRepository.findAll(criteria.getSpecification(), pageable);
        List<Product> products = pageData.getContent();
        List<String> productIds = products.stream()
                .map(Product::getId)
                .toList();
        // Map: Review
        Map<String, ProductReviewResponse> reviewStatsMap = productRepository
                .findReviewStatsByProductIds(productIds).stream()
                .collect(Collectors.toMap(ProductReviewResponse::getProductId, stats -> stats));
        // Map: product sold
        Map<String, Long> soldStatsMap = orderItemRepository
                .findSoldQuantitiesByProductIds(productIds, BaseConstant.ORDER_STATUS_DELIVERED).stream()
                .collect(Collectors.toMap(ProductSoldResponse::getProductId, ProductSoldResponse::getTotalSold));
        List<ProductResponse> productResponses = products.stream()
                .map(product -> {
                    ProductResponse response = productMapper.fromEntityToProductResponse(product);
                    ProductReviewResponse stats = reviewStatsMap.get(product.getId());
                    response.setTotalReviews(stats != null ? stats.getTotalReviews() : 0L);
                    response.setAverageRating(stats != null ? stats.getAverageRating() : 0.0);
                    response.setTotalSold(soldStatsMap.getOrDefault(product.getId(), 0L));
                    return response;
                })
                .toList();
        PaginationResponse<ProductResponse> responseDto = new PaginationResponse<>(
                productResponses,
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
        return BaseResponseUtils.success(responseDto, "Get product list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ProductResponse> get(@PathVariable String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_EXISTED));
        if (!product.getDiscount().isActive()) {
            product.setDiscount(null);
        }
        return BaseResponseUtils.success(productMapper.fromEntityToProductResponse(product), "Get product successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_CRE')")
    public BaseResponse<Void> create(@Valid @RequestBody CreateProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.RESOURCE_EXISTED);
        }
        // Find CATEGORY
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_EXISTED));
        // Find TAG
        List<Tag> tags = tagRepository.findAllByIdIn(request.getTagIds());

        Product product = productMapper.fromCreateProductRequest(request);
        product.setCategory(category);
        product.setTags(tags);
        product.setStatus(BaseConstant.PRODUCT_STATUS_SELLING);
        product.setImages(request.getImages());

        productRepository.save(product);

        return BaseResponseUtils.success(null, "Create product successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_UDP')")
    public BaseResponse<Void> update(@Valid @RequestBody UpdateProductRequest request) {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_EXISTED));
        // Update name
        if (!product.getName().equals(request.getName())) {
            if (tagRepository.existsByName(request.getName())) {
                throw new BadRequestException(ErrorCode.RESOURCE_EXISTED);
            }
        }
        // Update CATEGORY
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_EXISTED));
            product.setCategory(category);
        }
        if (!product.getTags().isEmpty()) {
            List<Tag> tags = tagRepository.findAllByIdIn(request.getTagIds());
            product.setTags(tags);
        }
        productMapper.updateFromUpdateProductRequest(product, request);
        product.setImages(request.getImages());
        productRepository.save(product);
        return BaseResponseUtils.success(null, "Update product successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_DEL')")
    public BaseResponse<Void> delete(@PathVariable String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_EXISTED));

        // Delete PRODUCT_TAG
        product.getTags().clear();
        productRepository.save(product);

        // Delete PRODUCT
        productRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Delete product successfully");
    }
}
