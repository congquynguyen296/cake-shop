package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);
    boolean existsByCategoryId(String categoryId);
}
