package com.cakeshop.api_main.repository.internal;

import com.cakeshop.api_main.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review, String>, JpaSpecificationExecutor<Review> {
    @Query(value = """
            SELECT 
                r.rate AS rate,
                COUNT(*) AS count,
                (SELECT COUNT(*) FROM tbl_review WHERE product_id = :productId) AS total,
                (SELECT AVG(rate) FROM tbl_review WHERE product_id = :productId) AS average
            FROM tbl_review r
            WHERE r.product_id = :productId
            GROUP BY r.rate
            """, nativeQuery = true)
    List<Object[]> getReviewStats(@Param("productId") String productId);
}
