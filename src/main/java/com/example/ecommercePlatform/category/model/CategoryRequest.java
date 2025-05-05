package com.example.ecommercePlatform.category.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private Set<Long> productIds;
}
