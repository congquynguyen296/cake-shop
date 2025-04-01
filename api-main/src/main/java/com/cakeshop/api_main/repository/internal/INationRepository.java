package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface INationRepository extends JpaRepository<Nation, String>, JpaSpecificationExecutor<Nation> {
    boolean existsByName(String name);
    boolean existsByParent(Nation parent);
}
