package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_order_item")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends Abstract {
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "note")
    String note;

    @Column(name = "unit_price")
    Double unitPrice = 0.0;

    @Column(name = "unit_discount_percentage")
    Integer unitDiscountPercentage = 0;

    @Column(name = "total_price")
    Double totalPrice = 0.0;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
