package com.pick2cart.PickToCart.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PaymentInformation {
    private String cardholderName;
    private String cardNumber;
    private LocalDateTime expirationDate;
    private String cvv;

    @Override
    public String toString() {
        return "PaymentInformation{" +
                "cardholderName='" + cardholderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
