package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String>, JpaSpecificationExecutor<Cart> {
    Optional<Cart> findByCustomerAccountUsername(String username);
}
