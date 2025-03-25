package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

    Account findByUsername(String username);

    Account findByEmail(String email);
}
