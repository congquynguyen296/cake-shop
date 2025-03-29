package com.cakeshop.api_main.model.criteria;

import com.cakeshop.api_main.model.Product;
import com.cakeshop.api_main.model.criteria.base.BaseCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductCriteria extends BaseCriteria<Product> {
    private String name;
    private Integer status;

    @Override
    public Specification<Product> getSpecification() {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(getId())) {
                predicates.add(cb.equal(root.get("status"), getStatus()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
