package com.example.Moviez.Percistance.Entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Cinemas") // Or "cinemas" if Postgres lowercased the table name too
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Explicitly define the column name as it exists in Postgres
    @Column(name = "cinemaid")
    private Long cinemaId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    // ... constructors, getters, and setters remain unchanged ...
}

