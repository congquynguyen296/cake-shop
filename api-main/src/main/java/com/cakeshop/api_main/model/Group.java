package com.cakeshop.api_main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_group")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends Abstract {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "kind")
    Integer kind;

    @Column(name = "is_system_role")
    Boolean isSystemRole;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "tbl_permission_group",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    List<Permission> permissions = new ArrayList<>();
}
