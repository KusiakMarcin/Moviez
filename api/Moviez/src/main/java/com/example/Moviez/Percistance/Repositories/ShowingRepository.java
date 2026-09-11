package com.example.Moviez.Percistance.Repositories;

import com.example.Moviez.Percistance.Entities.Showing;
import com.example.Moviez.Percistance.dto.ShowingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowingRepository extends JpaRepository<Showing, Long> {

    // Finds showings in a specific city between a start and end time
    List<Showing> findByCinema_CityAndShowingTimeBetweenOrderByShowingTimeAsc(
            String city,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );

    List<Showing> findByCinema_CityAndShowingTimeAfterOrderByShowingTimeAsc(
            String city,
            LocalDateTime currentTime
    );
}
