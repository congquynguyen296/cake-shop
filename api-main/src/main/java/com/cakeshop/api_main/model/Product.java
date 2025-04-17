package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends Abstract {
    @Column(name = "name")
    String name;

    @Column(name = "price")
    Double price;

    @Column(name = "description")
    String description;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "status")
    Integer status;

    @ElementCollection
    @CollectionTable(
            name = "tbl_product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_url")
    @Builder.Default
    List<String> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToMany
    @JoinTable(
            name = "tbl_product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "discount_id")
    Discount discount;

    public boolean checkQuantity(int quantity) {
        return this.quantity >= quantity;
    }

    public Integer getDiscountPercentage() {
        return (discount != null && discount.getDiscountPercentage() != null)
                ? discount.getDiscountPercentage()
                : 0;
    }
}
