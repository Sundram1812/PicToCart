package com.pick2cart.PickToCart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems=new HashSet<>();
    private double totalPrice;
    private int totalItem;
    private int totalDiscountedPrice;
    private int discount;

//    @Override
//    public String toString() {
//        return "Cart{" +
//                "cartId=" + cartId +
//                ", user=" + user +
//                ", cartItems=" + cartItems +
//                ", totalPrice=" + totalPrice +
//                ", totalItem=" + totalItem +
//                ", totalDiscountedPrice=" + totalDiscountedPrice +
//                ", discount=" + discount +
//                '}';
//    }
}
