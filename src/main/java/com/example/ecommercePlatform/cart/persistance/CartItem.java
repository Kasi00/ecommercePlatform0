package com.example.ecommercePlatform.cart.persistance;

import com.example.ecommercePlatform.product.persistance.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="cartItem")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;


    private Long quantity;
    private Long price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore// Lazy load the Product
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;  // Association with Product

}
