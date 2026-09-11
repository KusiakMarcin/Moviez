package com.example.Moviez.Percistance.dto.payu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuOrderResponse {
    private String orderId;
    private String redirectUri;
}
