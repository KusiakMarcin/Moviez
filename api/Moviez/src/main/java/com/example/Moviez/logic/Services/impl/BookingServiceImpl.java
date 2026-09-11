package com.example.Moviez.logic.Services.impl;

import com.example.Moviez.Percistance.Entities.Showing;
import com.example.Moviez.Percistance.Entities.Ticket;
import com.example.Moviez.Percistance.Entities.User;
import com.example.Moviez.Percistance.Repositories.ShowingRepository;
import com.example.Moviez.Percistance.Repositories.TicketRepository;
import com.example.Moviez.Percistance.Repositories.UserRepository;
import com.example.Moviez.Percistance.helpers.BookingResult;
import com.example.Moviez.logic.Services.BookingService;
import com.example.Moviez.logic.Services.PayuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final TicketRepository ticketRepository;
    private final ShowingRepository showingRepository;
    private final UserRepository userRepository;
    private final PayuService payuService;

    public BookingServiceImpl(
            TicketRepository ticketRepository,
            ShowingRepository showingRepository,
            UserRepository userRepository,
            PayuService payuService) {
        this.ticketRepository = ticketRepository;
        this.showingRepository = showingRepository;
        this.userRepository = userRepository;
        this.payuService = payuService;
    }

    @Override
    public List<String> getBookedSeatsForShowing(Long showingId) {
        List<Ticket> bookedTickets = ticketRepository.findByShowing_ShowingId(showingId);
        return bookedTickets.stream()
                .map(ticket -> ticket.getSitRow() + ticket.getSitNumber())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResult createBooking(Long showingId, List<String> seats, String keycloakId, String email) {
        Showing showing = showingRepository.findById(showingId)
                .orElseThrow(() -> new RuntimeException("Showing not found"));

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    String userEmail = (email != null) ? email : "unknown@example.com";
                    return userRepository.save(new User(userEmail, keycloakId));
                });

        String extOrderId = "ORDER-" + java.util.UUID.randomUUID().toString();

        for (String seat : seats) {
            String row = seat.substring(0, 1);
            Integer number = Integer.parseInt(seat.substring(1));

            Ticket ticket = new Ticket(showing, user, number, row);
            ticket.setStatus("PENDING");
            ticket.setPayuOrderId(extOrderId);
            ticketRepository.save(ticket);
        }

        BigDecimal seatCount = new BigDecimal(seats.size());
        BigDecimal totalAmount = showing.getBasePrice().multiply(seatCount);
        int totalAmountInCents = totalAmount.multiply(new BigDecimal(100)).intValue();

        String payuUrl = payuService.createOrder(showing.getMovie().getTitle(), totalAmountInCents, extOrderId);

        // Return both values together!
        return new BookingResult(payuUrl, extOrderId);
    }
}