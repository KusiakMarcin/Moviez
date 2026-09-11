package com.example.Moviez.Controllers;

import com.example.Moviez.Percistance.Entities.Ticket;
import com.example.Moviez.Percistance.Repositories.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<Ticket>> getMyTickets(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        List<Ticket> tickets = ticketRepository.findByUser_KeycloakId(keycloakId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Ticket>> getTicketsByOrder(@PathVariable String orderId) {
        // We already created this repository method earlier!
        List<Ticket> tickets = ticketRepository.findByPayuOrderId(orderId);

        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tickets);
    }
}
