package com.example.Moviez.Percistance.dto.payu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuProduct {
    private String name;
    private Integer unitPrice;
    private Integer quantity;
}
