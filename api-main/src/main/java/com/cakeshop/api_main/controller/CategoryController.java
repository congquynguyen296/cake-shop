package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.category.CreateCategoryRequest;
import com.cakeshop.api_main.dto.request.category.UpdateCategoryRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.category.CategoryResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.CategoryMapper;
import com.cakeshop.api_main.model.Category;
import com.cakeshop.api_main.model.criteria.CategoryCriteria;
import com.cakeshop.api_main.repository.internal.ICategoryRepository;
import com.cakeshop.api_main.repository.internal.IProductRepository;
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

@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryController {
    ICategoryRepository categoryRepository;
    IProductRepository productRepository;

    CategoryMapper categoryMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<CategoryResponse>> list(
            @Valid @ModelAttribute CategoryCriteria categoryCriteria,
            Pageable pageable
    ) {
        Page<Category> pageData = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);

        PaginationResponse<CategoryResponse> responseDto = new PaginationResponse<>(
                categoryMapper.fromEntitiesToCategoryResponseList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return BaseResponseUtils.success(responseDto, "Get category list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<CategoryResponse> get(@PathVariable String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        return BaseResponseUtils.success(categoryMapper.fromEntityToCategoryResponse(category), "Get category successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_CRE')")
    public BaseResponse<Void> create(@Valid @RequestBody CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.CATEGORY_NAME_EXISTED_ERROR);
        }
        // Create CATEGORY
        Category category = categoryMapper.fromCreateCategoryRequest(request);
        categoryRepository.save(category);

        return BaseResponseUtils.success(null, "Create category successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_UDP')")
    public BaseResponse<Void> updateUser(@Valid @RequestBody UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));
        // Update name
        if (!category.getName().equals(request.getName())) {
            if (categoryRepository.existsByName(request.getName())) {
                throw new BadRequestException(ErrorCode.CATEGORY_NAME_EXISTED_ERROR);
            }
        }
        // Update CATEGORY
        categoryMapper.updateFromUpdateCategoryRequest(category, request);
        categoryRepository.save(category);

        return BaseResponseUtils.success(null, "Update category successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_DEL')")
    public BaseResponse<Void> delete(@PathVariable String id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        if (productRepository.existsByCategoryId(id)) {
            throw new BadRequestException(ErrorCode.CATEGORY_CANT_DELETE_RELATIONSHIP_WITH_PRODUCT_ERROR);
        }

        // Delete CATEGORY
        categoryRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Delete category successfully");
    }
}
