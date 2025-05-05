package com.example.ecommercePlatform.order.persistance;

import com.example.ecommercePlatform.product.persistance.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;
    private Long price;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore// Lazy load the Product
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;  // Association with Product


    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;



}
