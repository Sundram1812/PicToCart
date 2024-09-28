package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public CartItem addCartItem(Long userId, AddItemRequest addItemRequest) throws ResourceNotFoundException;
    public Cart findUserCart(Long userId);
}
