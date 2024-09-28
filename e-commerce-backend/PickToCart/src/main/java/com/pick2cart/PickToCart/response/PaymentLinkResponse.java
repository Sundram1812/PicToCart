package com.pick2cart.PickToCart.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentLinkResponse {
    private String payment_link_url;
    private String getPayment_link_id;
}
