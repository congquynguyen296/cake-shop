package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    boolean existsByName(String name);
}
