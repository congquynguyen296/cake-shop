package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.customer.UpdateCustomerRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.customer.CustomerResponse;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.CustomerMapper;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.utils.BaseResponseUtils;
import com.cakeshop.api_main.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerController {
    ICustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<CustomerResponse> get(@PathVariable String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_EXISTED));
        return BaseResponseUtils.success(customerMapper.fromEntityToCustomerResponse(customer), "Get customer successfully");
    }

    @GetMapping(value = "/get-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<CustomerResponse> getProfile() {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_EXISTED));
        return BaseResponseUtils.success(customerMapper.fromEntityToCustomerResponse(customer), "Get customer successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> update(UpdateCustomerRequest request) {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_EXISTED));
        customerMapper.updateFromUpdateCustomerRequest(customer, request);
        return BaseResponseUtils.success(null, "Update customer successfully");
    }
}