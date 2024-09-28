package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.Address;
import com.pick2cart.PickToCart.entity.Order;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.OrderException;
import com.pick2cart.PickToCart.service.serviceImpl.OrderServiceImpl;
import com.pick2cart.PickToCart.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderServiceImpl orderService;
    private UserServiceImpl userService;

    @Autowired
    public OrderController(OrderServiceImpl orderService, UserServiceImpl userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String jwt, @RequestBody Address address) throws OrderException {
        User user=userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/orderById")
    public ResponseEntity<Order> getOrderById(@RequestParam Long orderId) throws OrderException {
        Order orderById = orderService.findOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderById);
    }

    @GetMapping("/orderHistory")
    public ResponseEntity<List<Order>> getOrderHistoryOfUser(@RequestHeader("Authorization") String jwt) throws OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orderHistory = orderService.usersOrderHistory(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(orderHistory);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrderById(@RequestParam Long orderId) throws OrderException {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Order Deleted");
    }
}
