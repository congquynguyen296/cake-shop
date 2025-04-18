package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.customer.UpdateCustomerRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.customer.CustomerResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.CustomerMapper;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.utils.BaseResponseUtils;
import com.cakeshop.api_main.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
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
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND_ERROR));
        return BaseResponseUtils.success(customerMapper.fromEntityToCustomerResponse(customer), "Get customer successfully");
    }

    @GetMapping(value = "/get-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<CustomerResponse> getProfile() {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND_ERROR));
        return BaseResponseUtils.success(customerMapper.fromEntityToCustomerResponse(customer), "Get customer successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> update(@RequestBody @Valid UpdateCustomerRequest request) {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CUSTOMER_USERNAME_EXISTED_ERROR));
        customer.setPhoneNumber(request.getPhoneNumber());
        customerMapper.updateFromUpdateCustomerRequest(customer, request);
        customerRepository.save(customer);
        return BaseResponseUtils.success(null, "Update customer successfully");
    }
}