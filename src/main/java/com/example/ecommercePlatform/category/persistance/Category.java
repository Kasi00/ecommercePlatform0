package com.example.ecommercePlatform.category.persistance;

import com.example.ecommercePlatform.product.persistance.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore  // Prevent infinite recursion by not serializing the list of products
    private Set<Product> products =new HashSet<>();
}
