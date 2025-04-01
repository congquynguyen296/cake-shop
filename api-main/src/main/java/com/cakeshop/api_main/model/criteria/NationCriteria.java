package com.cakeshop.api_main.model.criteria;

import com.cakeshop.api_main.model.Nation;
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
public class NationCriteria extends BaseCriteria<Nation> {
    private String name;
    private Integer kind;
    private String parentId;

    @Override
    public Specification<Nation> getSpecification() {
        return (Root<Nation> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }
            if (getKind() != null) {
                predicates.add(cb.equal(root.get("kind"), getKind()));
            }
            if (StringUtils.hasText(getParentId())) {
                predicates.add(cb.equal(root.get("parent").get("id"), getParentId()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
