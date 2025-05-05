package com.example.ecommercePlatform.cart;

import com.example.ecommercePlatform.cart.model.CartRequest;
import com.example.ecommercePlatform.cart.persistance.Cart;
import com.example.ecommercePlatform.cart.persistance.CartItem;
import com.example.ecommercePlatform.cart.persistance.CartRepository;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        cartService.getCarts();
        return ResponseEntity.ok().body(cartService.getCarts());

    }


    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getItems() {
        cartService.getCartItems();
        return ResponseEntity.ok().body(cartService.getCartItems());
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        cartRepository.findById(cartId);
        return ResponseEntity.ok().body(cartService.getCart(cartId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Long quantity) {

        cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok("Item added to cart successfully");
    }













}
