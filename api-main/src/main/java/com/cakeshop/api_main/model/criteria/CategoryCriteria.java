package com.cakeshop.api_main.model.criteria;

import com.cakeshop.api_main.model.Category;
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
public class CategoryCriteria extends BaseCriteria<Category> {
    private String code;
    private String name;

    @Override
    public Specification<Category> getSpecification() {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(getCode())) {
                predicates.add(cb.equal(root.get("code"), getCode()));
            }
            if (StringUtils.hasText(getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
