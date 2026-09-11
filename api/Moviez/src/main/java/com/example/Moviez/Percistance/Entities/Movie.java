package com.example.Moviez.Percistance.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieid") // Already fixed earlier
    private Long movieId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "durationminutes", nullable = false) // Explicitly lowercase
    private Integer durationMinutes;

    @Column(name = "posterurl") // Explicitly lowercase
    private String posterUrl;




}
