package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, Long> {

    @Query("SELECT r From Rating r Where r.product.productId =:productId")
    public List<Rating> getAllRatingsOfProduct(@Param("productId")Long productId);
}
