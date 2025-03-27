package com.cakeshop.api_main.model.criteria.base;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public abstract class BaseCriteria<T> implements Serializable {
    private String id;
    private Date createdAt;
    private Date updatedAt;

    public Specification<T> getBaseSpecification() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(getId())) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (getCreatedAt() != null) {
                predicates.add(cb.equal(root.get("createdAt"), getCreatedAt()));
            }
            if (getUpdatedAt() != null) {
                predicates.add(cb.equal(root.get("updatedAt"), getUpdatedAt()));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    public abstract Specification<T> getSpecification();
}
