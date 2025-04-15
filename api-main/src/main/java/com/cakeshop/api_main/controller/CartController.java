package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.cart.AddToCartRequest;
import com.cakeshop.api_main.dto.request.cartItem.CreateCartItemRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.cart.CartResponse;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.CartMapper;
import com.cakeshop.api_main.model.Cart;
import com.cakeshop.api_main.model.CartItem;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.repository.internal.ICartItemRepository;
import com.cakeshop.api_main.repository.internal.ICartRepository;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.repository.internal.IProductRepository;
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

import java.util.*;
import java.util.stream.Collectors;

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

    CartMapper cartMapper;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<CartResponse> get() {
        String username = SecurityUtil.getCurrentUsername();
        Cart cart = cartRepository.findByCustomerAccountUsername(username).orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByAccountUsername(username)
                    .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }
        return BaseResponseUtils.success(cartMapper.fromEntityToCartResponse(cart), "Get cart successfully");
    }

    @PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> addToCart(
            @Valid @RequestBody AddToCartRequest request
    ) {
        String username = SecurityUtil.getCurrentUsername();
        Cart cart = cartRepository.findByCustomerAccountUsername(username).orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByAccountUsername(username)
                    .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
            cart = Cart.builder()
                    .customer(customer)
                    .cartItems(new ArrayList<>())
                    .build();
        }
        List<String> productIds = request.getCartItems().stream()
                .map(CreateCartItemRequest::getProductId)
                .collect(Collectors.toList());
        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            List<String> foundIds = products.stream()
                    .map(Product::getId)
                    .toList();
            List<String> missingIds = productIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            if (!missingIds.isEmpty()) {
                throw new NotFoundException("Products not found: " + missingIds, ErrorCode.RESOURCE_NOT_EXISTED);
            }
        }
        Map<Product, Integer> productQuantityMap = new HashMap<>();
        for (CreateCartItemRequest item : request.getCartItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Unexpected error: Product not in fetched list"));
            if (!product.getDiscount().isActive()) {
                productRepository.updateDiscount(null);
            }
            productQuantityMap.put(product, item.getQuantity());
        }
        cart.addItems(productQuantityMap);
        cartRepository.save(cart);
        return BaseResponseUtils.success(null, "Added items to cart successfully");
    }

    @PostMapping(value = "/add-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> addToCart(
            @Valid @RequestBody CreateCartItemRequest request
    ) {
        String username = SecurityUtil.getCurrentUsername();
        Cart cart = cartRepository.findByCustomerAccountUsername(username).orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByAccountUsername(username)
                    .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
            cart = Cart.builder()
                    .customer(customer)
                    .cartItems(new ArrayList<>())
                    .build();
        }
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        if (product.getDiscount() != null && !product.getDiscount().isActive()) {
            productRepository.updateDiscount(null);
        }
        cart.addItem(product, request.getQuantity());
        cartRepository.save(cart);
        return BaseResponseUtils.success(null, "Added item to cart successfully");
    }
}
