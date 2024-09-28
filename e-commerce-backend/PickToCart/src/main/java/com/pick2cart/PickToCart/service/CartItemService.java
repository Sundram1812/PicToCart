package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.exception.CartItemException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.UserException;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws CartItemException, UserException, ResourceNotFoundException;
    public CartItem isCartItemExist(Cart cart, Product product, String size , Long userId);
    public void  removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException, ResourceNotFoundException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
