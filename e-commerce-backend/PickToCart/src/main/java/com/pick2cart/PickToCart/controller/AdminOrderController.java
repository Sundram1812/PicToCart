package com.pick2cart.PickToCart.controller;
import com.pick2cart.PickToCart.entity.Order;
import com.pick2cart.PickToCart.exception.OrderException;
import com.pick2cart.PickToCart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/order")
public class AdminOrderController {
    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/placed")
    public ResponseEntity<Order> placedOrder(@RequestParam Long orderId) throws OrderException {
        Order placedOrder = orderService.placedOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(placedOrder);
    }

    @PutMapping("/confirmed")
    public ResponseEntity<Order> confirmedOrder(@RequestParam Long orderId) throws OrderException {
        Order confirmedOrder = orderService.confirmedOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(confirmedOrder);
    }

    @PutMapping("/shipped")
    public ResponseEntity<Order> shippedOrder(@RequestParam Long orderId) throws OrderException {
        Order shippedOrder = orderService.shippedOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(shippedOrder);
    }

    @PutMapping("/delivered")
    public ResponseEntity<Order> deliveredOrder(@RequestParam Long orderId) throws OrderException {
        Order deliveredOrder = orderService.deliveredOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(deliveredOrder);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Order> cancelOrder(@RequestParam Long orderId) throws OrderException {
        Order canceledOrder = orderService.canceledOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(canceledOrder);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrderById(@RequestParam Long orderId) throws OrderException {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Order Deleted");
    }


}
