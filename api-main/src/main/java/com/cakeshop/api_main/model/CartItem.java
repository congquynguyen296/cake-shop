package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_cart_item")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem extends Abstract {
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity")
    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;
}
