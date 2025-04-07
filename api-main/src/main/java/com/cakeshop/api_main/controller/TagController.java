package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.tag.CreateTagRequest;
import com.cakeshop.api_main.dto.request.tag.UpdateTagRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.tag.TagResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.TagMapper;
import com.cakeshop.api_main.model.Tag;
import com.cakeshop.api_main.model.criteria.TagCriteria;
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

@Slf4j
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagController {
    ITagRepository tagRepository;
    TagMapper tagMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<TagResponse>> list(
            @Valid @ModelAttribute TagCriteria criteria,
            Pageable pageable
    ) {
        Page<Tag> pageData = tagRepository.findAll(criteria.getSpecification(), pageable);

        PaginationResponse<TagResponse> responseDto = new PaginationResponse<>(
                tagMapper.fromEntitiesToTagResponseList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return BaseResponseUtils.success(responseDto, "Get tag list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<TagResponse> get(@PathVariable String id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_EXISTED));

        return BaseResponseUtils.success(tagMapper.fromEntityToTagResponse(tag), "Get tag successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('TAG_CRE')")
    public BaseResponse<Void> create(@Valid @RequestBody CreateTagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.RESOURCE_EXISTED);
        }
        // Create TAG
        Tag tag = tagMapper.fromCreateTagRequest(request);
        tagRepository.save(tag);

        return BaseResponseUtils.success(null, "Create tag successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('TAG_UDP')")
    public BaseResponse<Void> updateUser(@Valid @RequestBody UpdateTagRequest request) {
        Tag tag = tagRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_EXISTED));
        // Update name
        if (!tag.getName().equals(request.getName())) {
            if (tagRepository.existsByName(request.getName())) {
                throw new BadRequestException(ErrorCode.RESOURCE_EXISTED);
            }
        }
        // Update TAG
        tagMapper.updateFromUpdateTagRequest(tag, request);
        tagRepository.save(tag);

        return BaseResponseUtils.success(null, "Update tag successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('TAG_DEL')")
    public BaseResponse<Void> delete(@PathVariable String id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_EXISTED));

        // Delete PRODUCT_TAG
        tag.getProducts().clear();
        tagRepository.save(tag);

        // Delete TAG
        tagRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Delete tag successfully");
    }
}
