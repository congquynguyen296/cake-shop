package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITagRepository extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
    boolean existsByName(String name);

    List<Tag> findAllByIdIn(List<String> ids);
}
