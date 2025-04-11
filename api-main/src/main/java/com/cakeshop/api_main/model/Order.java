package com.cakeshop.api_main.model;

import com.cakeshop.api_main.constant.BaseConstant;
import com.cakeshop.api_main.dto.request.orderItem.OrderItemDetails;
import com.cakeshop.api_main.exception.BadRequestException;
import com.cakeshop.api_main.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_order")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends Abstract {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(name = "shipping_fee")
    Integer shippingFee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderStatus> orderStatuses = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_status_id")
    OrderStatus currentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "payment_method")
    Integer paymentMethod;

    @Column(name = "total_amount")
    Double totalAmount = 0.0;

    @Column(name = "total_discount")
    Double totalDiscount = 0.0;

    public Order(Customer customer, Integer shippingFee, Integer paymentMethod) {
        this.customer = customer;
        this.shippingFee = shippingFee;
        this.paymentMethod = paymentMethod;
    }

    public void makeOrder(List<OrderItemDetails> orderItemList) {
        initializeOrderStatus();

        initializeOrderItems(orderItemList);

        calculateTotalAmount();
    }

    public void calculateTotalAmount() {
        double itemsTotal = orderItems.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        this.totalAmount = itemsTotal + (shippingFee != null ? shippingFee : 0);
    }

    private void initializeOrderStatus() {
        OrderStatus orderStatus = new OrderStatus(BaseConstant.ORDER_STATUS_PENDING, new Date(), this);
        this.currentStatus = orderStatus;
        this.orderStatuses.add(orderStatus);
    }

    private void initializeOrderItems(List<OrderItemDetails> orderItemList) {
        this.orderItems = orderItemList.stream()
                .map(entry -> {
                    Product product = entry.getProduct();
                    int quantity = entry.getQuantity();
                    String note = entry.getNote();
                    if (!product.checkQuantity(quantity)) {
                        throw new BadRequestException(
                                "Insufficient quantity for product: " + product.getId(),
                                ErrorCode.INVALID_FORM_ERROR);
                    }
                    OrderItem orderItem = new OrderItem(product, quantity, note, this);
                    orderItem.calculateTotalPrice();
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

}
