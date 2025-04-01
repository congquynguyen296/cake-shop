package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review, String>, JpaSpecificationExecutor<Review> {
}
