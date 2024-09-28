package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.CartItemException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.UserException;
import com.pick2cart.PickToCart.repository.CartItemRepo;
import com.pick2cart.PickToCart.repository.CartRepo;
import com.pick2cart.PickToCart.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepo cartItemRepo;
    private UserServiceImpl userServiceImpl;
    private CartRepo cartRepo;

    @Autowired
    public CartItemServiceImpl(CartItemRepo cartItemRepo, UserServiceImpl userServiceImpl, CartRepo cartRepo) {
        this.cartItemRepo = cartItemRepo;
        this.userServiceImpl = userServiceImpl;
        this.cartRepo = cartRepo;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        CartItem createdCartItem = cartItemRepo.save(cartItem);


        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws CartItemException, UserException, ResourceNotFoundException {
        CartItem oldCartItem=cartItemRepo.findById(cartItemId).orElseThrow(()->new CartItemException("Item does not exist with given id"));
        User user=userServiceImpl.findUserById(oldCartItem.getUserId());

        if(user.getUserId()==userId){
            oldCartItem.setQuantity(oldCartItem.getQuantity()+cartItem.getQuantity());

            oldCartItem.setPrice(oldCartItem.getProduct().getPrice() * oldCartItem.getQuantity());
            oldCartItem.setDiscountedPrice(oldCartItem.getProduct().getDiscountedPrice() * oldCartItem.getQuantity());

        }
        return cartItemRepo.save(oldCartItem);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem existingCartItem = cartItemRepo.isCartItemExist(cart, product, size, userId);
        return existingCartItem;

    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException, ResourceNotFoundException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userServiceImpl.findUserById(cartItem.getUserId());
        User loggedinUser=userServiceImpl.findUserById(userId);
        if( loggedinUser.getUserId()== user.getUserId()){
            cartItemRepo.delete(cartItem);
        }else {
            throw new UserException("You can not remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> item = cartItemRepo.findById(cartItemId);
        if(item.isPresent()){
            return item.get();
        }
        throw new CartItemException("Cart Item not found with id: "+cartItemId);
    }
}
