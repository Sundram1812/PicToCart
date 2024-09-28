package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.OrderItem;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.OrderItemException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderItemService {
    public OrderItem createOrderItem(Long userId,Long productId,OrderItem orderItem) throws OrderItemException, ResourceNotFoundException;
    public OrderItem updateOrderItem(Long userId, Long orderItemId, OrderItem orderItem) throws OrderItemException;
    public String removeOrderItem(Long userId, Long orderItemId) throws OrderItemException;
    public OrderItem findOrderItemById(Long orderItemId) throws OrderItemException;
    public List<OrderItem> findAllOrderItem() throws OrderItemException;

}
