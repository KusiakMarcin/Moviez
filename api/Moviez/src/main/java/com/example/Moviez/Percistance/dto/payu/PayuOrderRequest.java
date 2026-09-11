package com.example.Moviez.Percistance.dto.payu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuOrderRequest {
    private String notifyUrl;
    private String continueUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private Integer totalAmount;
    private String extOrderId;
    private List<PayuProduct> products;
}
