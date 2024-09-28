package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.Address;
import com.pick2cart.PickToCart.entity.Order;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.OrderException;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress) throws OrderException;
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> usersOrderHistory(Long userId) throws OrderException;
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public  Order deliveredOrder(Long orderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrders() throws OrderException;
    public void deleteOrder(Long orderId) throws OrderException;
}
