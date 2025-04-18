package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.dto.request.product.CreateProductRequest;
import com.cakeshop.api_main.dto.request.product.UpdateProductRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.product.ProductResponse;
import com.cakeshop.api_main.dto.response.product.ProductSoldResponse;
import com.cakeshop.api_main.dto.response.review.ReviewStatsResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.ProductMapper;
import com.cakeshop.api_main.model.Category;
import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.model.Tag;
import com.cakeshop.api_main.model.criteria.ProductCriteria;
import com.cakeshop.api_main.repository.internal.*;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
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
    IReviewRepository reviewRepository;

    ProductMapper productMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<ProductResponse>> list(
            @Valid @ModelAttribute ProductCriteria criteria,
            Pageable pageable
    ) {
        criteria.setStatus(BaseConstant.PRODUCT_STATUS_SELLING);
        Page<Product> pageData = productRepository.findAll(criteria.getSpecification(), pageable);
        List<Product> products = pageData.getContent();
        List<String> productIds = products.stream()
                .map(Product::getId)
                .toList();
        // Map: product sold
        Map<String, Long> soldStatsMap = orderItemRepository
                .findSoldQuantitiesByProductIds(productIds, BaseConstant.ORDER_STATUS_DELIVERED).stream()
                .collect(Collectors.toMap(ProductSoldResponse::getProductId, ProductSoldResponse::getTotalSold));
        List<ProductResponse> productResponses = products.stream()
                .map(product -> {
                    ProductResponse response = productMapper.fromEntityToProductResponse(product);
                    response.setImage(product.getImages().get(0));
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
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));
        if (!product.getDiscount().isActive()) {
            product.setDiscount(null);
        }
        ProductResponse productResponse = productMapper.fromEntityToProductResponse(product);

        // sold quantity stats
        ProductSoldResponse productSoldResponse = orderItemRepository.findSoldQuantityByProductId(id, BaseConstant.ORDER_STATUS_DELIVERED);
        Long totalSold = productSoldResponse != null ? productSoldResponse.getTotalSold() : 0L;
        productResponse.setTotalSold(totalSold);

        // review stats
        List<Object[]> result = reviewRepository.getReviewStats(id);
        ReviewStatsResponse reviewStatsResponse = getReviewStatsResponse(id, result);
        productResponse.setReviewStats(reviewStatsResponse);
        return BaseResponseUtils.success(productResponse, "Get product successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_CRE')")
    public BaseResponse<Void> create(@Valid @RequestBody CreateProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.PRODUCT_NAME_EXISTED_ERROR);
        }
        // Find CATEGORY
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));
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
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));
        // Update name
        if (!product.getName().equals(request.getName())) {
            if (tagRepository.existsByName(request.getName())) {
                throw new BadRequestException(ErrorCode.PRODUCT_NAME_EXISTED_ERROR);
            }
        }
        // Update CATEGORY
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));
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
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));

        // Delete PRODUCT_TAG
        product.getTags().clear();
        productRepository.save(product);

        // Delete PRODUCT
        productRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Delete product successfully");
    }

    private static ReviewStatsResponse getReviewStatsResponse(String id, List<Object[]> result) {
        Map<Integer, Long> reviewMap = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            reviewMap.put(i, 0L);
        }

        long total = 0L;
        double average = 0.0;

        if (!result.isEmpty()) {
            // Chuyển đổi an toàn cho total và average
            Object totalObj = result.get(0)[2];
            Object avgObj = result.get(0)[3];

            if (totalObj instanceof BigInteger) {
                total = ((BigInteger) totalObj).longValue();
            } else if (totalObj instanceof Long) {
                total = (Long) totalObj;
            }

            if (avgObj instanceof BigDecimal) {
                average = ((BigDecimal) avgObj).doubleValue();
            } else if (avgObj instanceof Double) {
                average = (Double) avgObj;
            }

            for (Object[] row : result) {
                Integer rate = (Integer) row[0];

                Object countObj = row[1];
                Long count = 0L;
                if (countObj instanceof BigInteger) {
                    count = ((BigInteger) countObj).longValue();
                } else if (countObj instanceof Long) {
                    count = (Long) countObj;
                }

                reviewMap.put(rate, count);
            }
        }

        return new ReviewStatsResponse(id, total, average, reviewMap);
    }

}
