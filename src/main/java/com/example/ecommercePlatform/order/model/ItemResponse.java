package com.example.ecommercePlatform.order.model;

import com.example.ecommercePlatform.product.persistance.Product;
import lombok.Data;

@Data
public class ItemResponse {
    private Product product;
    private Long quantity;
    private Long price;
    private String name;
}
