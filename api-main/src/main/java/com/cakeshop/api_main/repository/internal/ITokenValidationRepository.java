package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.TokenValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenValidationRepository extends JpaRepository<TokenValidation, String> {
}
