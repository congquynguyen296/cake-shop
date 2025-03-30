package com.cakeshop.api_main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends Abstract {

    @Column(name = "code")
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "description" ,  columnDefinition = "TEXT")
    String description;

    @Column(name = "image")
    String image;
}
