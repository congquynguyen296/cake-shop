package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.dto.request.order.CreateOrderRequest;
import com.cakeshop.api_main.dto.request.order.UpdateOrderStatusRequest;
import com.cakeshop.api_main.dto.request.orderItem.CreateOrderItemRequest;
import com.cakeshop.api_main.dto.request.orderItem.OrderItemDetails;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.dto.response.PaginationResponse;
import com.cakeshop.api_main.dto.response.order.OrderResponse;
import com.cakeshop.api_main.dto.response.orderStatus.OrderStatusResponse;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import com.cakeshop.api_main.exception.NotFoundException;
import com.cakeshop.api_main.mapper.OrderMapper;
import com.cakeshop.api_main.mapper.OrderStatusMapper;
import com.cakeshop.api_main.model.Customer;
import com.cakeshop.api_main.model.Order;
import com.cakeshop.api_main.model.OrderStatus;
import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.model.criteria.OrderCriteria;
import com.cakeshop.api_main.repository.internal.ICustomerRepository;
import com.cakeshop.api_main.repository.internal.IOrderRepository;
import com.cakeshop.api_main.repository.internal.IProductRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderController {
    ICustomerRepository customerRepository;
    IProductRepository productRepository;
    IOrderRepository orderRepository;

    OrderMapper orderMapper;
    private final OrderStatusMapper orderStatusMapper;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<OrderResponse> get(@PathVariable String id) {
        String username = SecurityUtil.getCurrentUsername();
        Order order = orderRepository.findByIdAndCustomerAccountUsername(id, username)
                .orElseThrow(() -> new NotFoundException("ORDER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));

        return BaseResponseUtils.success(orderMapper.fromEntityToOrderResponse(order), "Get cart successfully");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PaginationResponse<OrderResponse>> list(
            @Valid @ModelAttribute OrderCriteria criteria,
            Pageable pageable
    ) {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        criteria.setCustomerId(customer.getId());
        Page<Order> pageData = orderRepository.findAll(criteria.getSpecification(), pageable);
        PaginationResponse<OrderResponse> responseDto = new PaginationResponse<>(
                orderMapper.fromEntitiesToOrderResponseList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
        return BaseResponseUtils.success(responseDto, "Get order list successfully");
    }

    @GetMapping(value = "/list-order-status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<OrderStatusResponse>> listOrderStatus(@PathVariable String id) {
        String username = SecurityUtil.getCurrentUsername();
        Order order = orderRepository.findByIdAndCustomerAccountUsername(id, username)
                .orElseThrow(() -> new NotFoundException("ORDER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        List<OrderStatus> orderStatuses = order.getOrderStatuses();
        return BaseResponseUtils.success(orderStatusMapper.fromEntitiesToOrderStatusResponseList(orderStatuses), "Get order status list successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> create(@Valid @RequestBody CreateOrderRequest request) {
        String username = SecurityUtil.getCurrentUsername();
        Customer customer = customerRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));

        List<String> productIds = request.getOrderItems().stream()
                .map(CreateOrderItemRequest::getProductId)
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
        List<OrderItemDetails> orderItemDetailsList = new ArrayList<>();
        for (CreateOrderItemRequest item : request.getOrderItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("Unexpected error: Product not in fetched list", ErrorCode.INVALID_FORM_ERROR));
            if (product.getDiscount() != null && !product.getDiscount().isActive()) {
                productRepository.updateDiscount(null);
            }
            orderItemDetailsList.add(new OrderItemDetails(product, item.getQuantity(), item.getNote()));
        }

        Order order = new Order(customer, request.getShippingFee(), request.getPaymentMethod());
        order.makeOrder(orderItemDetailsList);
        orderRepository.save(order);

        return BaseResponseUtils.success(null, "Create order successfully");
    }

    @PutMapping(value = "/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> updateStatus(
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        String username = SecurityUtil.getCurrentUsername();
        Order order = orderRepository.findByIdAndCustomerAccountUsername(request.getId(), username)
                .orElseThrow(() -> new NotFoundException("ORDER_NOT_FOUND", ErrorCode.RESOURCE_NOT_EXISTED));
        if (order.getCurrentStatus().getStatus().equals(request.getStatus() - 1) || request.getStatus().equals(BaseConstant.ORDER_STATUS_CANCELED)) {
            OrderStatus orderStatus = new OrderStatus(request.getStatus(), new Date(), order);
            order.setCurrentStatus(orderStatus);
            order.getOrderStatuses().add(orderStatus);
        } else {
            throw new BadRequestException("Unexpected error: Order not in fetched list", ErrorCode.INVALID_FORM_ERROR);
        }
        orderRepository.save(order);
        return BaseResponseUtils.success(null, "Update order status successfully");
    }
}
