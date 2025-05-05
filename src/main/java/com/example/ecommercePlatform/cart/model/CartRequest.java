package com.example.ecommercePlatform.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartRequest {
    private Long productId;
    private Long quantity;

}
