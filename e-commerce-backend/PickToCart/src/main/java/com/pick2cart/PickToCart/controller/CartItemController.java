package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.exception.CartItemException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.UserException;
import com.pick2cart.PickToCart.service.serviceImpl.CartItemServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartItemController {
    private CartItemServiceImpl cartItemService;

    public CartItemController(CartItemServiceImpl cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/cartItem")
    private ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem){
        CartItem savedCartItem = cartItemService.createCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCartItem);
    }

    @GetMapping("/cartItem")
    private ResponseEntity<CartItem> getCartItemById(@RequestParam Long cartItemId) throws CartItemException {
        CartItem cartItemById = cartItemService.findCartItemById(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemById);
    }

    @PutMapping("/cartItem/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestParam Long userId, @RequestParam Long cartItemId, @RequestBody CartItem cartItem ) throws CartItemException, UserException, ResourceNotFoundException {
        CartItem updatedCartItem = cartItemService.updateCartItem(userId, cartItemId, cartItem);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCartItem);
    }

//    implements IsExist cart item

    @DeleteMapping("/cartItem/delete")
    public ResponseEntity<String> removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException, ResourceNotFoundException {
        cartItemService.removeCartItem(userId, cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body("Item Deleted");
    }
}
