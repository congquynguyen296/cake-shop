package com.cakeshop.api_main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_permission")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends Abstract {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "code")
    String code;

    @Column(name = "action")
    String action;
}
