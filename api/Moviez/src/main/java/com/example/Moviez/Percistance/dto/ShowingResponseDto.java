package com.example.Moviez.Percistance.dto;

import java.time.LocalDateTime;

public record ShowingResponseDto(
        Long showingId,
        String movieTitle,
        int durationMinutes,
        String posterUrl,
        String cinemaName,
        String city,
        LocalDateTime showingTime
) {}