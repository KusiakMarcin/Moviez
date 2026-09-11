package com.example.Moviez.Percistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private String payuRedirectUrl;
    private String extOrderId;
}
