package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.request.AddItemRequest;
import com.pick2cart.PickToCart.service.serviceImpl.CartServiceImpl;
import com.pick2cart.PickToCart.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private UserServiceImpl userService;
    private CartServiceImpl cartService;

    @Autowired
    public CartController(UserServiceImpl userService, CartServiceImpl cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.createCart(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }


    @PutMapping("/addToCart")
    public ResponseEntity<CartItem> addItemToCart(@RequestParam Long userId, @RequestBody AddItemRequest addItemRequest) throws ResourceNotFoundException {
//        System.out.println(userId);
//        System.out.println(addItemRequest);
        CartItem addedCartItem = cartService.addCartItem(userId, addItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCartItem);
    }

    @GetMapping
    public ResponseEntity<Cart> getUserCart(@RequestHeader("Authorization") String jwt){
        User user=userService.findUserProfileByJwt(jwt);
        Cart userCart = cartService.findUserCart(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userCart);
    }
}
