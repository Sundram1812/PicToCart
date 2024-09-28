package com.pick2cart.PickToCart.request;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class ReviewsReq {
    private Long productId;
    private String review;
}
