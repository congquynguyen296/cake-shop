package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, String>, JpaSpecificationExecutor<Address> {
    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.customer.id = :customerId AND a.isDefault = true")
    void resetDefaultAddressesByCustomerId(@Param("customerId") String customerId);
}
