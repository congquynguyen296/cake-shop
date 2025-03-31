package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "tbl_order_status")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatus extends Abstract {
    @Column(name = "status")
    Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    Date date;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;
}
