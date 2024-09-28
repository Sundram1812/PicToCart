package com.pick2cart.PickToCart.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddItemRequest {
    private Long productId;
    private String size;
    private int quantity;
    private Integer price;
}
