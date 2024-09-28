package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.Rating;
import com.pick2cart.PickToCart.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Reviews, Long> {

    @Query("SELECT r From Reviews r Where r.product.productId=:productId")
    public List<Reviews> getAllReviewsOfProduct(@Param("productId")Long productId);
}
