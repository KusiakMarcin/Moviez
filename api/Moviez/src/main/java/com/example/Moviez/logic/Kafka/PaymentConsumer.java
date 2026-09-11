package com.example.Moviez.logic.Kafka;

import com.example.Moviez.Percistance.Entities.Ticket;
import com.example.Moviez.Percistance.Repositories.TicketRepository;
import com.example.Moviez.Percistance.dto.PaymentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PaymentConsumer {

    private final TicketRepository ticketRepository;
    private final ObjectMapper objectMapper;

    public PaymentConsumer(TicketRepository ticketRepository, ObjectMapper objectMapper) {
        this.ticketRepository = ticketRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payment-notifications", groupId = "cinema-booking-group")
    @Transactional
    public void consume(String jsonMessage) {
        try {
            PaymentEvent event = objectMapper.readValue(jsonMessage, PaymentEvent.class);

            System.out.println("Kafka Consumer: Processing order " + event.getExtOrderId() + " with status: " + event.getStatus());

            List<Ticket> tickets = ticketRepository.findByPayuOrderId(event.getExtOrderId());

            if ("COMPLETED".equals(event.getStatus())) {
                for (Ticket ticket : tickets) {
                    ticket.setStatus("PAID");
                    ticketRepository.save(ticket);
                }
                System.out.println("Success: Tickets updated to PAID!");
            } else if ("CANCELED".equals(event.getStatus())) {
                for (Ticket ticket : tickets) {
                    ticket.setStatus("CANCELED");
                    ticketRepository.save(ticket);
                }
                System.out.println("Notice: Tickets marked CANCELED.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
