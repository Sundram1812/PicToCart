package com.pick2cart.PickToCart.response;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean status;
}
