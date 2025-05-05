package com.example.ecommercePlatform.user.Persistance;

import com.example.ecommercePlatform.cart.persistance.Cart;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;



    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private Cart cart;




    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role>  roles= new HashSet<>();
}
