package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String> {
    Permission findByCode(String code);
    boolean existsByCode(String code);
    boolean existsByName(String name);
}
