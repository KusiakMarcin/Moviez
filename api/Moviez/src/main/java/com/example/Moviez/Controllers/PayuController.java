package com.example.Moviez.Controllers;

import com.example.Moviez.Percistance.dto.payu.PayuNotificationPayload;
import com.example.Moviez.Percistance.dto.PaymentEvent;
import com.example.Moviez.logic.Kafka.PayuProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payu")
public class PayuController {

    private final PayuProducer payuProducer;

    public PayuController(PayuProducer payuProducer) {
        this.payuProducer = payuProducer;
    }

    @PostMapping("/notify")
    public ResponseEntity<String> handlePayuNotification(@RequestBody PayuNotificationPayload payload) {

        if (payload == null || payload.getOrder() == null) {
            return ResponseEntity.badRequest().body("Invalid payload");
        }

        String extOrderId = payload.getOrder().getExtOrderId();
        String status = payload.getOrder().getStatus();

        System.out.println("Webhook received from PayU -> Order: " + extOrderId + " | Status: " + status);


        if ("COMPLETED".equals(status)) {
            payuProducer.sendPaymentEvent(new PaymentEvent(extOrderId, "PAID"));
        } else if ("CANCELED".equals(status) || "REJECTED".equals(status)) {
            // Send an event to update tickets to CANCELLED in the database
            payuProducer.sendPaymentEvent(new PaymentEvent(extOrderId, "CANCELLED"));
        }

        // PayU expects a 200 OK response, otherwise it will keep retrying the webhook
        return ResponseEntity.ok("OK");
    }
}