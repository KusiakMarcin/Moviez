package com.example.Moviez.logic.Services;


import com.example.Moviez.Percistance.helpers.BookingResult;

import java.util.List;

public interface BookingService {
    List<String> getBookedSeatsForShowing(Long showingId);

    // Handles tickets creation and PayU order generation
    BookingResult createBooking(Long showingId, List<String> seats, String keycloakId, String email);
}
