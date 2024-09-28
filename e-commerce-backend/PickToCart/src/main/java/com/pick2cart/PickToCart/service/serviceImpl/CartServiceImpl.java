package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.CartItem;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.mapper.ProductMapper;
import com.pick2cart.PickToCart.repository.CartRepo;
import com.pick2cart.PickToCart.request.AddItemRequest;
import com.pick2cart.PickToCart.service.CartService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    private CartRepo cartRepo;
    private CartItemServiceImpl cartItemService;
    private ProductServiceImpl productService;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo, CartItemServiceImpl cartItemService, ProductServiceImpl productService) {
        this.cartRepo = cartRepo;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    @Override
    public CartItem addCartItem(Long userId, AddItemRequest addItemRequest) throws ResourceNotFoundException {
        Cart cart=cartRepo.findByUserId(userId);
        ProductDto productDto = productService.findProductById(addItemRequest.getProductId());
        Product product= ProductMapper.productDtoToProduct(productDto, new Product());
        product.setProductId(addItemRequest.getProductId());

        CartItem isCartItemPresent=cartItemService.isCartItemExist(cart,product, addItemRequest.getSize(),userId);

//        CartItem cartItem=new CartItem();
        CartItem savedCartItem=null;
        if(isCartItemPresent==null){
            CartItem cartItem=new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(addItemRequest.getQuantity());
            cartItem.setProduct(product);
            cartItem.setUserId(userId);

            int price=addItemRequest.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(addItemRequest.getSize());
            savedCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(savedCartItem);
            cartRepo.save(cart);
        }


        return savedCartItem;
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepo.findByUserId(userId);
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;


        for (CartItem cartItem : cart.getCartItems()){
            totalItem += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscount(totalItem-totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        System.out.println(cart);
        return cartRepo.save(cart);
    }

//    public Cart updateCart()
}
