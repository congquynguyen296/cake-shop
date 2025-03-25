package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "tbl_token_validation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenValidation {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "expiry_date")
    Date expiryDate;
}
