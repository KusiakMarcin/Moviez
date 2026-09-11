package com.example.Moviez.logic.Services.impl;

import com.example.Moviez.Percistance.dto.ShowingResponseDto;
import com.example.Moviez.Percistance.Entities.Showing;
import com.example.Moviez.Percistance.Repositories.ShowingRepository;
import com.example.Moviez.logic.Services.ShowingService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service // This registers the class as a Spring Bean
public class ShowingServiceImpl implements ShowingService {

    private final ShowingRepository showingRepository;

    public ShowingServiceImpl(ShowingRepository showingRepository) {
        this.showingRepository = showingRepository;
    }

    @Override
    public List<ShowingResponseDto> getShowingsByLocationAndDate(String city, LocalDate date) {
        // Convert the date to a full 24-hour range
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // Fetch from DB
        List<Showing> showings = showingRepository
                .findByCinema_CityAndShowingTimeBetweenOrderByShowingTimeAsc(city, startOfDay, endOfDay);

        // Map Entities to JSON DTOs
        return showings.stream().map(showing -> new ShowingResponseDto(
                showing.getShowingId(),
                showing.getMovie().getTitle(),
                showing.getMovie().getDurationMinutes(),
                showing.getMovie().getPosterUrl(),
                showing.getCinema().getName(),
                showing.getCinema().getCity(),
                showing.getShowingTime()
        )).collect(Collectors.toList());
    }

    @Override
    public List<ShowingResponseDto> getUpcomingShowingsByLocation(String city) {
        List<Showing> showings = showingRepository
                .findByCinema_CityAndShowingTimeAfterOrderByShowingTimeAsc(city, LocalDateTime.now());

        return showings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Helper method to keep your code DRY (Don't Repeat Yourself)
    private ShowingResponseDto mapToDto(Showing showing) {
        return new ShowingResponseDto(
                showing.getShowingId(),
                showing.getMovie().getTitle(),
                showing.getMovie().getDurationMinutes(),
                showing.getMovie().getPosterUrl(),
                showing.getCinema().getName(),
                showing.getCinema().getCity(),
                showing.getShowingTime()
        );
    }
}