package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.entity.*;
import com.pick2cart.PickToCart.exception.OrderException;
import com.pick2cart.PickToCart.repository.AddressRepo;
import com.pick2cart.PickToCart.repository.OrderItemRepo;
import com.pick2cart.PickToCart.repository.OrderRepo;
import com.pick2cart.PickToCart.repository.UserRepo;
import com.pick2cart.PickToCart.service.CartService;
import com.pick2cart.PickToCart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepo orderRepo;
    private UserRepo userRepo;
    private OrderItemRepo orderItemRepo;
    private AddressRepo addressRepo;
    private CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, UserRepo userRepo,OrderItemRepo orderItemRepo,AddressRepo addressRepo,CartService cartService) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderItemRepo =orderItemRepo;
        this.addressRepo=addressRepo;
        this.cartService=cartService;
    }

//    @Override
//    public Order createOrder(User user, Address shippingAddress) {
//        String orderId = UUID.randomUUID().toString();
//
//
//        Optional<User> userById = userRepo.findById(user.getUserId());
//        shippingAddress.setUser(userById.get());
//        Address savedAddress = addressRepo.save(shippingAddress);
//        user.getAddresses().add(savedAddress);
//        Long userId=userById.get().getUserId();
//        System.out.println("userId -----------------"+userId);
//
//        Cart userCart = cartService.findUserCart(userId);
//
//        Set<CartItem> cartItems=userCart.getCartItems();
//        List<OrderItem> orderItems=new ArrayList<>();
//
//        for (CartItem cartItem : cartItems){
//            OrderItem orderItem=new OrderItem();
//            orderItem.setPrice(cartItem.getPrice());
//            orderItem.setSize(cartItem.getSize());
//            orderItem.setUserId(cartItem.getUserId());
//            orderItem.setProduct(cartItem.getProduct());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
//
//            OrderItem createdOrderItem = orderItemRepo.save(orderItem);
//
//            orderItems.add(createdOrderItem);
//        }
//
//        Order order=new Order();
//        order.setUser(user);
//        order.setShippingAddress(shippingAddress);
//        order.setOrderId(orderId);
//        order.setTotalPrice(userCart.getTotalPrice());
//        order.setTotalDiscountedPrice(userCart.getTotalDiscountedPrice());
//        order.setDiscount(userCart.getDiscount());
//        order.setTotalItem(userCart.getTotalItem());
//        order.setOrderItems(orderItems);
//        order.setOrderStatus("pending");
//        order.setCreatedAt(LocalDateTime.now());
//        order.setOrderDate(LocalDateTime.now());
//        order.setDeliveryDate(LocalDateTime.now());
//        Order savedOrder = orderRepo.save(order);
//
////        Save order into all order items
//        for (OrderItem orderItem: orderItems){
//            orderItem.setOrder(savedOrder);
//            orderItemRepo.save(orderItem);
//        }
//
//        return savedOrder;
//    }





    @Override
    public Order createOrder(User user, Address shippingAddress) throws OrderException {
        String orderId = UUID.randomUUID().toString();

        System.out.println(shippingAddress);
        Optional<User> userById = userRepo.findById(user.getUserId());
        if (!userById.isPresent()) {
            throw new OrderException("User not found with id: " + user.getUserId());
        }

        shippingAddress.setUser(userById.get());
        Address savedAddress =null;

        if(shippingAddress.getAddressId() == 0L){
            savedAddress = addressRepo.save(shippingAddress);
            user.getAddresses().add(savedAddress);
        }
        Long userId = userById.get().getUserId();
        System.out.println("userId -----------------" + userId);

        Cart userCart = cartService.findUserCart(userId);
        Set<CartItem> cartItems = userCart.getCartItems();
        List<OrderItem> orderItems = new CopyOnWriteArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setSize(cartItem.getSize());
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepo.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        int totalPrice=0;
        int discountedPrice=0;

        for (OrderItem item: orderItems){
            totalPrice = totalPrice + item.getPrice() * item.getQuantity();
            discountedPrice= discountedPrice + item.getDiscountedPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setUser(user);

        if(savedAddress==null){
            order.setShippingAddress(shippingAddress);
        }else {
            order.setShippingAddress(savedAddress);
        }


        order.setOrderId(orderId);
//        order.setTotalPrice(userCart.getTotalPrice());
//        order.setTotalDiscountedPrice(userCart.getTotalDiscountedPrice());
        order.setTotalPrice(totalPrice);
        order.setTotalDiscountedPrice(discountedPrice);
        order.setDiscount(userCart.getDiscount());
        order.setTotalItem(userCart.getTotalItem());
        order.setOrderItems(orderItems);
        order.setOrderStatus("pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDate(LocalDateTime.now());
//        savedAddress.setOrder(order);
        Order savedOrder = orderRepo.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(savedOrder);
            orderItemRepo.save(orderItem);
        }
//        savedAddress.setOrder(savedOrder);
//        Updating Address after adding Order to it
//        addressRepo.save(savedAddress);
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isEmpty()){
            throw new OrderException("Order doesn't exist with id: "+orderId);
        }

        return optionalOrder.get();
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) throws OrderException{
        List<Order> orderHistoryOfUser = orderRepo.findOrderHistoryOfUser(userId);
        List<OrderItem> orderItems = orderItemRepo.findByUserId(userId);
        orderHistoryOfUser.stream().forEach((order)->order.setOrderItems(orderItems));
        if(orderHistoryOfUser==null){
            throw new OrderException("User with id:"+ userId+" doesn't have Order History");
        }
        return orderHistoryOfUser;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        Order updatedOrderStatus = orderRepo.save(order);
        return updatedOrderStatus;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        Order updatedOrderStatus = orderRepo.save(order);
        return updatedOrderStatus;

    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        Order updatedOrderStatus = orderRepo.save(order);
        return updatedOrderStatus;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        Order updatedOrderStatus = orderRepo.save(order);
        return updatedOrderStatus;
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        Order updatedOrderStatus = orderRepo.save(order);
        return updatedOrderStatus;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> allOrders = orderRepo.findAll();
        return allOrders;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order orderById = findOrderById(orderId);
        orderRepo.deleteById(orderId);
    }
}
