package com.pick2cart.PickToCart.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingReq {
    private Long productId;
    private double rating;
}
