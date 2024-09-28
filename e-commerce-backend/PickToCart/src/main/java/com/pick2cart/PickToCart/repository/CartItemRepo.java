package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci From CartItem ci Where ci.cart= :cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart")Cart cart,
                                    @Param("product")Product product,
                                    @Param("size") String size,
                                    @Param("userId") Long userId);
}
