package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.address.CreateAddressRequest;
import com.cakeshop.api_main.dto.request.address.UpdateAddressRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.address.AddressResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.AddressMapper;
import com.cakeshop.api_main.model.Address;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.model.Nation;
import com.cakeshop.api_main.model.criteria.AddressCriteria;
import com.cakeshop.api_main.repository.internal.IAddressRepository;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.repository.internal.INationRepository;
import com.cakeshop.api_main.utils.BaseResponseUtils;
import com.cakeshop.api_main.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressController {
    IAddressRepository addressRepository;
    ICustomerRepository customerRepository;
    INationRepository nationRepository;

    AddressMapper addressMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<AddressResponse>> list(Pageable pageable) {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        AddressCriteria addressCriteria = new AddressCriteria();
        addressCriteria.setCustomerId(customer.getId());
        Page<Address> pageData = addressRepository.findAll(addressCriteria.getSpecification(), pageable);
        PaginationResponse<AddressResponse> responseDto = new PaginationResponse<>(
                addressMapper.fromEntitiesToAddressResponseList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return BaseResponseUtils.success(responseDto, "Get address list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AddressResponse> get(@PathVariable String id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_EXISTED));

        return BaseResponseUtils.success(addressMapper.fromEntityToAddressResponse(address), "Get address successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> create(
            @Valid @RequestBody CreateAddressRequest request,
            BindingResult bindingResult
    ) {
        // Get current customer
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));

        // Get all nation from form
        List<Nation> nations = nationRepository.findAllById(Arrays.asList(
                request.getProvinceId(),
                request.getDistrictId(),
                request.getCommuneId()
        ));

        Map<String, Nation> nationMap = nations.stream().collect(Collectors.toMap(Nation::getId, n -> n));

        Nation province = getNationOrFail(nationMap, request.getProvinceId());
        Nation district = getNationOrFail(nationMap, request.getDistrictId());
        Nation commune = getNationOrFail(nationMap, request.getCommuneId());

        if (province == null || district == null || commune == null) {
            throw new NotFoundException("NATION_ERROR_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED);
        }
        if (isInvalidParent(district, province) || isInvalidParent(commune, district)) {
            throw new BadRequestException("NATION_ERROR_NOT_ALLOW_HAVE_PARENT", ErrorCode.INVALID_FORM_ERROR);
        }

        Address address = Address.builder()
                .province(province)
                .district(district)
                .commune(commune)
                .details(request.getDetails())
                .isDefault(request.getIsDefault())
                .build();

        if (address.getIsDefault()) {
            addressRepository.resetDefaultAddressesByCustomerId(customer.getId());
        }
        addressRepository.save(address);
        return BaseResponseUtils.success(null, "Create address successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> updateUser(
            @Valid @RequestBody UpdateAddressRequest request,
            BindingResult bindingResult
    ) {
        Address address = addressRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("ADDRESS_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));

        // Get current customer
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));

        // Get all nation from form
        List<Nation> nations = nationRepository.findAllById(Arrays.asList(
                request.getProvinceId(),
                request.getDistrictId(),
                request.getCommuneId()
        ));

        Map<String, Nation> nationMap = nations.stream().collect(Collectors.toMap(Nation::getId, n -> n));

        Nation province = getNationOrFail(nationMap, request.getProvinceId());
        Nation district = getNationOrFail(nationMap, request.getDistrictId());
        Nation commune = getNationOrFail(nationMap, request.getCommuneId());

        if (province == null || district == null || commune == null) {
            throw new NotFoundException("NATION_ERROR_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED);
        }
        if (isInvalidParent(district, province) || isInvalidParent(commune, district)) {
            throw new BadRequestException("NATION_ERROR_NOT_ALLOW_HAVE_PARENT", ErrorCode.INVALID_FORM_ERROR);
        }
        address.setProvince(province);
        address.setDistrict(district);
        address.setCommune(commune);
        address.setDetails(request.getDetails());
        address.setIsDefault(request.getIsDefault());

        if (address.getIsDefault()) {
            addressRepository.resetDefaultAddressesByCustomerId(customer.getId());
        }
        addressRepository.save(address);
        return BaseResponseUtils.success(null, "Create address successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> delete(@PathVariable String id) {
        // Get current customer
        String username = SecurityUtil.getCurrentUsername();
        customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ADDRESS_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        // Delete NATION
        addressRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Delete address successfully");
    }

    @PutMapping(value = "/set-default/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> setDefault(@PathVariable String id) {
        // Get current customer
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ADDRESS_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        addressRepository.resetDefaultAddressesByCustomerId(customer.getId());
        addressRepository.save(address);
        return BaseResponseUtils.success(null, "Set default address successfully");
    }

    // check nation
    private Nation getNationOrFail(Map<String, Nation> nationMap, String id) {
        return nationMap.get(id);
    }

    // check parent nation
    private boolean isInvalidParent(Nation child, Nation parent) {
        return !Objects.equals(child.getParent().getId(), parent.getId());
    }
}
