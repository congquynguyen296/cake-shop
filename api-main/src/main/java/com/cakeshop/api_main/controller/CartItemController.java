package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.cartItem.UpdateCartItemRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.model.CartItem;
import com.cakeshop.api_main.repository.internal.ICartItemRepository;
import com.cakeshop.api_main.repository.internal.ICartRepository;
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
@RequestMapping("/cart-item")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemController {
    ICartRepository cartRepository;
    ICartItemRepository cartItemRepository;

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> update(
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CART_ITEM_NOT_FOUND_ERROR));
        if (request.getQuantity() == 0) {
            cartItemRepository.deleteById(request.getCartItemId());
        } else {
            cartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(cartItem);
        }
        return BaseResponseUtils.success(null, "Updated cart item successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> delete(@PathVariable String id) {
        String username = SecurityUtil.getCurrentUsername();
        cartRepository.findByCustomerAccountUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CART_NOT_FOUND_ERROR));
        cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CART_ITEM_NOT_FOUND", ErrorCode.CART_ITEM_NOT_FOUND_ERROR));
        cartItemRepository.deleteById(id);
        return BaseResponseUtils.success(null, "Deleted Cart Item");
    }
}
