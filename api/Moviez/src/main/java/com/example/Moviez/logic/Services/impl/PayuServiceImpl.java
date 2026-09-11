package com.example.Moviez.logic.Services.impl;


import com.example.Moviez.Percistance.dto.payu.PayuOrderRequest;
import com.example.Moviez.Percistance.dto.payu.PayuOrderResponse;
import com.example.Moviez.Percistance.dto.payu.PayuProduct;
import com.example.Moviez.Percistance.dto.payu.PayuTokenResponse;
import com.example.Moviez.logic.Services.PayuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PayuServiceImpl implements PayuService {

    @Value("${spring.payu.api.payu-token}")
    private String payuTokenUrl;

    @Value("${spring.payu.api.payu-order}")
    private String payuOrderUrl;

    @Value("${spring.payu.shop.client-id}")
    private String clientId;

    @Value("${spring.payu.shop.client-secret}")
    private String clientSecret;

    private final RestClient restClient;

    public PayuServiceImpl() {
        this.restClient = RestClient.create();
    }

    private String getAccessToken() {
        // Debug check to guarantee it loads
        System.out.println("DEBUG - PayU Token URL loaded: " + payuTokenUrl);

        if (payuTokenUrl == null || payuTokenUrl.isEmpty()) {
            throw new IllegalStateException("PayU token URL is not configured properly!");
        }

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        System.out.println("DEBUG - Client ID: " + clientId);
        System.out.println("DEBUG - Client Secret Length: " + (clientSecret != null ? clientSecret.length() : "null"));

        PayuTokenResponse response = restClient.post()
                .uri(payuTokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(PayuTokenResponse.class);

        return response.getAccess_token();
    }

    @Override
    public String createOrder(String movieTitle, int totalAmountInCents, String extOrderId) {
        String token = getAccessToken();


        PayuOrderRequest orderRequest = new PayuOrderRequest(
                "https://stipulate-unsheathe-prism.ngrok-free.dev/api/payu/notify",
                "http://localhost:5173/payment-success",
                "127.0.0.1",
                clientId,
                "Cinema Tickets - " + movieTitle,
                "PLN",
                totalAmountInCents,
                extOrderId, // Use the parameter
                List.of(new PayuProduct("Tickets", totalAmountInCents, 1))
        );

        PayuOrderResponse response = restClient.post()
                .uri(payuOrderUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderRequest)
                .retrieve()
                .body(PayuOrderResponse.class);

        return response.getRedirectUri();
    }
}
