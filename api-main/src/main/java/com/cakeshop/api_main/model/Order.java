package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "order")
    List<OrderStatus> orderStatuses = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "current_status_id")
    OrderStatus currentStatus;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "payment_method")
    Integer paymentMethod;

    @Column(name = "total_amount")
    Double totalAmount = 0.0;

    @Column(name = "total_discount")
    Double totalDiscount = 0.0;
}
