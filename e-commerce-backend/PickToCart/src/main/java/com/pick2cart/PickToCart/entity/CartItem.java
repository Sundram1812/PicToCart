package com.pick2cart.PickToCart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Cart cart;

    @ManyToOne( fetch = FetchType.EAGER)
    private Product product;
    private String size;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;

//    @Override
//    public String toString() {
//        return "CartItem{" +
//                "cartItemId=" + cartItemId +
//                ", cart=" + cart +
//                ", product=" + product +
//                ", size='" + size + '\'' +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                ", discountedPrice=" + discountedPrice +
//                ", userId=" + userId +
//                '}';
//    }
}
