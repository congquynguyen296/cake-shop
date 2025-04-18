package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.dto.request.nation.CreateNationRequest;
import com.cakeshop.api_main.dto.request.nation.UpdateNationRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.nation.NationResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.NationMapper;
import com.cakeshop.api_main.model.Nation;
import com.cakeshop.api_main.model.criteria.NationCriteria;
import com.cakeshop.api_main.repository.internal.INationRepository;
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

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/nation")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NationController {
    INationRepository nationRepository;
    NationMapper nationMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<NationResponse>> list(
            @Valid @ModelAttribute NationCriteria nationCriteria,
            Pageable pageable
    ) {
        Page<Nation> pageData = nationRepository.findAll(nationCriteria.getSpecification(), pageable);

        PaginationResponse<NationResponse> responseDto = new PaginationResponse<>(
                nationMapper.fromEntitiesToNationResponseList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return BaseResponseUtils.success(responseDto, "Get nation list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<NationResponse> get(@PathVariable String id) {
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NATION_NOT_FOUND_ERROR));

        return BaseResponseUtils.success(nationMapper.fromEntityToNationResponse(nation), "Get nation successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('NAT_CRE')")
    public BaseResponse<Void> create(@Valid @RequestBody CreateNationRequest request) {
        if (nationRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.NATION_NAME_EXISTED_ERROR);
        }
        // Create Nation
        Nation nation = nationMapper.fromCreateNationRequest(request);
        Nation parent = Optional.ofNullable(request.getParentId())
                .flatMap(nationRepository::findById)
                .orElse(null);
        // check nation parent valid
        validateNationParent(parent, nation);

        nation.setParent(parent);
        nationRepository.save(nation);
        return BaseResponseUtils.success(null, "Create nation successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('NAT_UDP')")
    public BaseResponse<Void> updateUser(@Valid @RequestBody UpdateNationRequest request) {
        Nation nation = nationRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NATION_NOT_FOUND_ERROR));
        // Update name
        if (!nation.getName().equals(request.getName())) {
            if (nationRepository.existsByName(request.getName())) {
                throw new BadRequestException(ErrorCode.NATION_NAME_EXISTED_ERROR);
            }
        }
        if (!Objects.equals(nation.getKind(), request.getKind())) {
            if (nationRepository.existsByParent(nation)) {
                throw new BadRequestException(ErrorCode.NATION_CANT_UPDATE_RELATIONSHIP_WITH_ADDRESS_ERROR);
            }
        }
        nationMapper.updateFromUpdateNationRequest(nation, request);
        if (request.getParentId() != null) {
            // check nation parent valid
            Nation parent = nationRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NATION_PARENT_NOT_FOUND_ERROR));
            validateNationParent(parent, nation);
            nation.setParent(parent);
        }
        nationRepository.save(nation);
        // Update CATEGORY
        return BaseResponseUtils.success(null, "Update nation successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_DEL')")
    public BaseResponse<Void> delete(@PathVariable String id) {
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NATION_NOT_FOUND_ERROR));
        if (nationRepository.existsByParent(nation)) {
            throw new BadRequestException(ErrorCode.NATION_CANT_DELETE_RELATIONSHIP_WITH_ADDRESS_ERROR);
        }
        // Delete NATION
        nationRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Delete nation successfully");
    }

    private void validateNationParent(Nation parent, Nation nation) {
        boolean isProvince = Objects.equals(nation.getKind(), BaseConstant.NATION_KIND_PROVINCE);
        // If the nation is a District, it must not have a parent
        if (isProvince && parent != null) {
            throw new BadRequestException(ErrorCode.NATION_PROVINCE_NOT_HAVE_PARENT_ERROR);
        }
        // If the nation is not a District, it must have a parent
        if (!isProvince && parent == null) {
            throw new NotFoundException(ErrorCode.NATION_PARENT_INVALID_ERROR);
        }
        // Validate the parent's level: the parent must be exactly one level lower than the nation
        if (parent != null && parent.getKind() != nation.getKind() - 1) {
            throw new BadRequestException(ErrorCode.NATION_PARENT_INVALID_ERROR);
        }
    }
}
