package com.pick2cart.PickToCart.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionRespone {
    private String message;
    private HttpStatus httpStatus;
}
