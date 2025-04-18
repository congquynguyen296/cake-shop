package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.cartItem.CreateCartItemRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.cart.CartResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.CartMapper;
import com.cakeshop.api_main.model.Cart;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.model.Tag;
import com.cakeshop.api_main.repository.internal.ICartRepository;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.repository.internal.IProductRepository;
import com.cakeshop.api_main.repository.internal.ITagRepository;
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

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartController {
    ICustomerRepository customerRepository;
    ICartRepository cartRepository;
    IProductRepository productRepository;
    ITagRepository tagRepository;

    CartMapper cartMapper;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<CartResponse> get() {
        String username = SecurityUtil.getCurrentUsername();
        Cart cart = cartRepository.findByCustomerAccountUsername(username).orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByAccountUsername(username)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND_ERROR));
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }
        return BaseResponseUtils.success(cartMapper.fromEntityToCartResponse(cart), "Get cart successfully");
    }

    @PostMapping(value = "/add-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> addToCart(
            @Valid @RequestBody CreateCartItemRequest request
    ) {
        String username = SecurityUtil.getCurrentUsername();
        Cart cart = cartRepository.findByCustomerAccountUsername(username).orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByAccountUsername(username)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND_ERROR));
            cart = Cart.builder()
                    .customer(customer)
                    .cartItems(new ArrayList<>())
                    .build();
        }
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND", ErrorCode.PRODUCT_NOT_FOUND_ERROR));
        if (product.getDiscount() != null && !product.getDiscount().isActive()) {
            productRepository.updateDiscount(null);
        }
        if (!product.checkQuantity(request.getQuantity())) {
            throw new BadRequestException(ErrorCode.PRODUCT_QUANTITY_NOT_ENOUGH_ERROR);
        }
        Tag tag = tagRepository.findById(request.getTagId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TAG_NOT_FOUND_ERROR));
        cart.addItem(product, tag, request.getQuantity());
        cartRepository.save(cart);
        return BaseResponseUtils.success(null, "Added item to cart successfully");
    }
}
