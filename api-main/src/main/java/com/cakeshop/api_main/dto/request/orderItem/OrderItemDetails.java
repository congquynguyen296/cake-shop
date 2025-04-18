package com.cakeshop.api_main.dto.request.orderItem;

import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.model.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDetails {
    Product product;
    Tag tag;
    int quantity;
}
