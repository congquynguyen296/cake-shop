package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_address")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends Abstract {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id", nullable = false)
    Nation province;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id", nullable = false)
    Nation district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commune_id", nullable = false)
    Nation commune;

    @Column(name = "details")
    String details;

    @Column(name = "is_default")
    Boolean isDefault;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "phone_number")
    String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
}
