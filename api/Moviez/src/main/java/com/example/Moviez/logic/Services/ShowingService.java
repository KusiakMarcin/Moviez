package com.example.Moviez.logic.Services;

import com.example.Moviez.Percistance.dto.ShowingResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface ShowingService {
    List<ShowingResponseDto> getShowingsByLocationAndDate(String city, LocalDate date);
    List<ShowingResponseDto> getUpcomingShowingsByLocation(String city);
}
