package com.example.ecommercePlatform.product.model;

import com.example.ecommercePlatform.category.persistance.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private Long price;
    private Long stock;
    private String imageUrl;
    private Long categoryId;
}
