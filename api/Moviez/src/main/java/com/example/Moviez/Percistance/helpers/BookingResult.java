package com.example.Moviez.Percistance.helpers;


import lombok.AllArgsConstructor;
import lombok.Getter; // Changed from @Data to avoid final setter conflict
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResult {
    private String payuUrl;
    private String extOrderId;
}
