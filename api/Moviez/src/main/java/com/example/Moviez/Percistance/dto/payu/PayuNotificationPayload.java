package com.example.Moviez.Percistance.dto.payu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayuNotificationPayload {
    private PayuWebhookOrder order;
    private String localReceiptDateTime;
    private List<Object> properties; // <-- Fixed from String to List<Object> (or you can delete this line entirely)

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PayuWebhookOrder {
        private String orderId;
        private String extOrderId;
        private String orderCreateDate;
        private String notifyUrl;
        private String customerIp;
        private String merchantPosId;
        private String description;
        private String currencyCode;
        private String totalAmount;
        private String status;
    }
}