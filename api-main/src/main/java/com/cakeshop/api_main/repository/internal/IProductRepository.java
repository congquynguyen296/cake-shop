package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.dto.response.product.ProductReviewResponse;
import com.cakeshop.api_main.model.Discount;
import com.cakeshop.api_main.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);

    boolean existsByCategoryId(String categoryId);

    @Modifying
    @Query("UPDATE Product p SET p.discount = :discount")
    void updateDiscount(@Param("discount") Discount discount);

    @Modifying
    @Query("UPDATE Product p SET p.discount = :discount WHERE p.category.id = :categoryId")
    void updateDiscount(@Param("discount") Discount discount, @Param("categoryId") String categoryId);

    @Query("""
            SELECT new com.cakeshop.api_main.dto.response.product.ProductReviewResponse(
                r.product.id, COUNT(r), AVG(r.rate)
            )
            FROM Review r
            WHERE r.product.id IN :productIds
            GROUP BY r.product.id
            """)
    List<ProductReviewResponse> findReviewStatsByProductIds(@Param("productIds") List<String> productIds);
}
