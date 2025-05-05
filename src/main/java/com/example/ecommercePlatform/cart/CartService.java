package com.example.ecommercePlatform.cart;

import com.example.ecommercePlatform.cart.model.CartRequest;
import com.example.ecommercePlatform.cart.persistance.Cart;
import com.example.ecommercePlatform.cart.persistance.CartItem;
import com.example.ecommercePlatform.cart.persistance.CartItemRepository;
import com.example.ecommercePlatform.cart.persistance.CartRepository;
import com.example.ecommercePlatform.product.persistance.Product;
import com.example.ecommercePlatform.product.persistance.ProductRepository;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;





    public void addItemToCart(Long userId, Long productId, Long quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        if (cart == null) {
            // Create a new cart if it doesn't exist
            cart = new Cart();
            cart.setUser(user); // Set the user for the new cart
            cart.setItems(new ArrayList<>());

            // Save the new cart to the database
            cartRepository.save(cart);

            // Link the new cart to the user
            user.setCart(cart);
            userRepository.save(user);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        // Add the item to the cart
        cart.getItems().add(cartItem);

        // Update the total price of the cart
        Long totalPrice = cart.getItems().stream()
                .mapToLong(item -> item.getPrice() * item.getQuantity())
                .sum();

        cart.setTotalPrice(totalPrice);
        System.out.println("Calculated total: " + totalPrice);
        System.out.println("Cart items: " + cart.getItems().size());

        // Save the cart again after adding the item
        cartRepository.saveAndFlush(cart);
    }

    public List<CartItem> getCartItems(){
        return cartItemRepository.findAll();
    }

    public Cart getCart(Long cartId){
         cartRepository.findById(cartId);
         return cartRepository.findById(cartId).get();
    }

    public List<Cart> getCarts(){
        return cartRepository.findAll();
    }



}
