package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.OrderItem;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.OrderItemException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.service.serviceImpl.OrderItemServiceImpl;
import com.pick2cart.PickToCart.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
    private OrderItemServiceImpl orderItemService;
    private UserServiceImpl userService;

    @Autowired
    public OrderItemController(OrderItemServiceImpl orderItemService, UserServiceImpl userService) {
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestParam Long productId, @RequestBody OrderItem orderItem , @RequestHeader("Authorization") String jwt ) throws OrderItemException, ResourceNotFoundException {
        User user = userService.findUserProfileByJwt(jwt);
        OrderItem createdOrderItem = orderItemService.createOrderItem(user.getUserId(), productId, orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderItem> updateOrderItem(@RequestParam Long orderItemId, @RequestBody OrderItem orderItem, @RequestHeader("Authorization") String jwt) throws OrderItemException {
        User user = userService.findUserProfileByJwt(jwt);
        OrderItem updateOrderItem = orderItemService.updateOrderItem(user.getUserId(), orderItemId, orderItem);
        return ResponseEntity.status(HttpStatus.OK).body(updateOrderItem);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrderItem(@RequestParam Long orderItemId, @RequestHeader("Authorization") String jwt) throws OrderItemException {
        User user = userService.findUserProfileByJwt(jwt);
        String message = orderItemService.removeOrderItem(user.getUserId(), orderItemId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping
    public ResponseEntity<OrderItem> getSingleOrderItem(@RequestParam Long orderItemId) throws OrderItemException {
        OrderItem orderItemById = orderItemService.findOrderItemById(orderItemId);
        return ResponseEntity.status(HttpStatus.OK).body(orderItemById);
    }

    @GetMapping("/getAllOrderItem")
    public ResponseEntity<List<OrderItem>> getAllOrderItem() throws OrderItemException {
        List<OrderItem> allOrderItem = orderItemService.findAllOrderItem();
        return ResponseEntity.status(HttpStatus.OK).body(allOrderItem);
    }
}
