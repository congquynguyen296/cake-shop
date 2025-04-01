package com.cakeshop.api_main.model.criteria;

import com.cakeshop.api_main.model.Address;
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
public class AddressCriteria extends BaseCriteria<Address> {
    private String customerId;

    @Override
    public Specification<Address> getSpecification() {
        return (Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(getCustomerId())) {
                predicates.add(cb.equal(root.get("customer").get("id"), getCustomerId()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
