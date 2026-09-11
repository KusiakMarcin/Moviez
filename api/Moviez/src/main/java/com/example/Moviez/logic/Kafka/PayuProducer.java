package com.example.Moviez.logic.Kafka;

import com.example.Moviez.Percistance.dto.PaymentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PayuProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PayuProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendPaymentEvent(PaymentEvent event) {
        try {
            // Convert the Java object to a JSON string
            String jsonMessage = objectMapper.writeValueAsString(event);
            System.out.println(">>> DEBUG: Sending to Kafka topic 'payment-notifications' with key: " + event.getExtOrderId());
            // Send to Kafka using the external Order ID as the message key
            kafkaTemplate.send("payment-notifications", event.getExtOrderId(), jsonMessage);

            System.out.println("Kafka Producer: Sent payment event for order -> " + event.getExtOrderId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert PaymentEvent to JSON string for Kafka", e);
        }
    }
}