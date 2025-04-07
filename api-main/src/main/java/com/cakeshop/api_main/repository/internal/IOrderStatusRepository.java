package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatus, String>, JpaSpecificationExecutor<OrderStatus> {
    List<OrderStatus> findByOrderId(String orderId);
}
