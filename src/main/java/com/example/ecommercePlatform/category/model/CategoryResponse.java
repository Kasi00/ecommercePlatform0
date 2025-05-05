package com.example.ecommercePlatform.category.model;

import com.example.ecommercePlatform.product.model.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private List<ProductResponse> products;
}
