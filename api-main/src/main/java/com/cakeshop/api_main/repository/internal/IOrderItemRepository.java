package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.dto.response.product.ProductSoldResponse;
import com.cakeshop.api_main.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, String>, JpaSpecificationExecutor<OrderItem> {
    @Query("""
            SELECT new com.cakeshop.api_main.dto.response.product.ProductSoldResponse(
                oi.product.id,
                SUM(oi.quantity)
            )
            FROM OrderItem oi
            WHERE oi.order.currentStatus.status = :status
            AND oi.product.id IN :productIds
            GROUP BY oi.product.id
            """)
    List<ProductSoldResponse> findSoldQuantitiesByProductIds(
            @Param("productIds") List<String> productIds,
            @Param("status") Integer status
    );
}
