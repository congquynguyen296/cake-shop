package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "tbl_cart")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends Abstract {
    @OneToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    List<CartItem> cartItems = new ArrayList<>();

    public void addItem(Product product, Tag tag, int quantity) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(this)
                    .product(product)
                    .tag(tag)
                    .quantity(quantity)
                    .build();
            cartItems.add(newItem);
        }
    }
}
