package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.repository.internal.ICartItemRepository;
import com.cakeshop.api_main.repository.internal.ICartRepository;
import com.cakeshop.api_main.utils.BaseResponseUtils;
import com.cakeshop.api_main.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemController {
    ICartRepository cartRepository;
    ICartItemRepository cartItemRepository;

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> delete(@PathVariable String id) {
        String username = SecurityUtil.getCurrentUsername();
        cartRepository.findByCustomerAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CART_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CART_ITEM_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        cartItemRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Deleted Cart Item");
    }
}
