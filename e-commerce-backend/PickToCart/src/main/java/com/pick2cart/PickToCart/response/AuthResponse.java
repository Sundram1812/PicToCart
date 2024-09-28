package com.pick2cart.PickToCart.response;

import lombok.*;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class AuthResponse {
    private String jwt;
    private String message;
}
