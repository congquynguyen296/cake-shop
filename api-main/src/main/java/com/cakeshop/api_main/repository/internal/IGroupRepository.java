package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, String> {
    Group findByKind(Integer kind);
}
