package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends Abstract {

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "avatar_path")
    String avatarPath;

    @Column(name = "is_active")
    Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    Group group;
}