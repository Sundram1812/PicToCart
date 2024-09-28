package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("SELECT o from Order o Where o.user.userId =:userId AND (o.orderStatus = 'PLACED' OR o.orderStatus = 'CONFIRMED')")
    public List<Order> findOrderHistoryOfUser(@Param("userId") Long userId);
}
