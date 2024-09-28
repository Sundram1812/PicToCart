package com.pick2cart.PickToCart.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
