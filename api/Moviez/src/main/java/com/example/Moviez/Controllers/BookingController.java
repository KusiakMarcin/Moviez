package com.example.Moviez.Controllers;

import com.example.Moviez.Percistance.dto.BookingRequest;
import com.example.Moviez.Percistance.dto.BookingResponse;
import com.example.Moviez.Percistance.helpers.BookingResult;
import com.example.Moviez.logic.Services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/showings/{showingId}/seats")
    public ResponseEntity<List<String>> getBookedSeats(@PathVariable Long showingId) {
        List<String> bookedSeats = bookingService.getBookedSeatsForShowing(showingId);
        return ResponseEntity.ok(bookedSeats);
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody BookingRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String keycloakId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        BookingResult result = bookingService.createBooking(
                request.getShowingId(),
                request.getSeats(),
                keycloakId,
                email
        );

        return ResponseEntity.ok(new BookingResponse(result.getPayuUrl(), result.getExtOrderId()));
    }
}


