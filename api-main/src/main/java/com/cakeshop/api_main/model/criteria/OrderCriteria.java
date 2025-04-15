package com.cakeshop.api_main.model.criteria;

import com.cakeshop.api_main.model.Order;
import com.cakeshop.api_main.model.criteria.base.BaseCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderCriteria extends BaseCriteria<Order> {

    String customerId;
    Integer status;
    Date createdAt;

    @Override
    public Specification<Order> getSpecification() {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(getCustomerId())) {
                predicates.add(cb.like(cb.lower(root.get("customer").get("id")), "%" + getCustomerId().toLowerCase() + "%"));
            }
            if (getStatus() != null) {
                predicates.add(cb.equal(root.get("currentStatus").get("status"), getStatus()));
            }

            assert query != null;
            query.orderBy(cb.desc(root.get("createdAt")));

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
