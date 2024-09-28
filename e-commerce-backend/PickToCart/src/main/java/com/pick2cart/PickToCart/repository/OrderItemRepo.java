package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    public List<OrderItem> findByUserId(Long userId);
}
