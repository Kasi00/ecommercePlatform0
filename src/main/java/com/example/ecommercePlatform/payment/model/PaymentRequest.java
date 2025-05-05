package com.example.ecommercePlatform.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private String currency;

}
