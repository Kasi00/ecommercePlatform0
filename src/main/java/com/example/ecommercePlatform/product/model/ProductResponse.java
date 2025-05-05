package com.example.ecommercePlatform.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long stock;
    private String imageUrl;
}
