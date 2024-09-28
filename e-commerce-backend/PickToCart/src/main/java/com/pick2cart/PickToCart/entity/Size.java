package com.pick2cart.PickToCart.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Size {
    private String name;
    private String quantity;

    @Override
    public String toString() {
        return "Size{" +
                "name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
