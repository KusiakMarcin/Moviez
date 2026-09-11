package com.example.Moviez.Percistance.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "Showings")
@Data
public class Showing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showingid") // Fixed primary key
    private Long showingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaid", nullable = false)
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieid", nullable = false)
    private Movie movie;

    @Column(name = "showingtime", nullable = false) // Explicitly lowercase
    private LocalDateTime showingTime;

    @Column(name = "baseprice", nullable = false) // Explicitly lowercase
    private BigDecimal basePrice;


}
