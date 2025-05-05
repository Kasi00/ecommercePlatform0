package com.example.ecommercePlatform.order.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private double totalAmount;
    private String status;
    private LocalDateTime placedAt;
    private List<ItemResponse> orderItems;
}
