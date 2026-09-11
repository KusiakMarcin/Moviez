package com.example.Moviez.Controllers;


import com.example.Moviez.Percistance.Entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Moviez.Percistance.dto.ShowingResponseDto;
import com.example.Moviez.logic.Services.ShowingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/showings")
public class ShowingController {

    private final ShowingService showingService;

    // Spring automatically injects the ShowingServiceImpl bean here
    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    @GetMapping
    public ResponseEntity<List<ShowingResponseDto>> getShowings(
            @RequestParam String city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<ShowingResponseDto> showings;

        if (date != null) {
            // If a specific date is provided, use your existing method
            showings = showingService.getShowingsByLocationAndDate(city, date);
        } else {
            // If no date is provided, fetch ALL future showings for this city
            showings = showingService.getUpcomingShowingsByLocation(city);
        }

        return ResponseEntity.ok(showings);
    }
}
