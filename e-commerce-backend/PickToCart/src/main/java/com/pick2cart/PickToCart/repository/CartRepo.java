package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepo extends JpaRepository<Cart,Long> {

    @Query("SELECT c From Cart c Where c.user.userId=:userId")
    public Cart findByUserId(@Param("userId") Long userId);
}
