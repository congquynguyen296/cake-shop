package com.cakeshop.api_main.model;

import com.cakeshop.api_main.exception.ErrorCode;
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
    @ManyToOne(cascade = CascadeType.MERGE)
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

    public OrderItem(Product product, Integer quantity, String note, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.note = note;
        this.order = order;
        this.unitPrice = product.getPrice();
        this.unitDiscountPercentage = product.getDiscountPercentage();
    }

    public void calculateTotalPrice() {
        double basePrice = unitPrice != null ? unitPrice : 0;
        int discount = unitDiscountPercentage != null ? unitDiscountPercentage : 0;
        int quantity = this.quantity != null ? this.quantity : 0;

        double discountAmount = basePrice * discount / 100.0;
        this.totalPrice = (basePrice - discountAmount) * quantity;
    }
}
