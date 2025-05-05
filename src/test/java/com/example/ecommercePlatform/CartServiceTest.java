package com.example.ecommercePlatform;

import com.example.ecommercePlatform.cart.CartService;
import com.example.ecommercePlatform.cart.persistance.Cart;
import com.example.ecommercePlatform.cart.persistance.CartItem;
import com.example.ecommercePlatform.cart.persistance.CartItemRepository;
import com.example.ecommercePlatform.cart.persistance.CartRepository;
import com.example.ecommercePlatform.product.persistance.Product;
import com.example.ecommercePlatform.product.persistance.ProductRepository;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testAddItemToCart() {
        Long userId = 1L;
        Long productId = 10L;
        Long quantity = 2L;

        User user= new User();
        user.setId(userId);

        Product product= new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(150L);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(0L);
        user.setCart(cart);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        cartService.addItemToCart(userId, productId, quantity);

        // Assert
        assertEquals(1, cart.getItems().size());
        CartItem addedItem = cart.getItems().get(0);
        assertEquals("Test Product", addedItem.getName());
        assertEquals(150L, addedItem.getPrice());
        assertEquals(quantity, addedItem.getQuantity());
        assertEquals(300L, cart.getTotalPrice());

        verify(cartRepository).saveAndFlush(cart);

    }

    @Test
    void shouldCreateCartIfNotExistAndAddItem() {
        // Arrange
        Long userId = 1L;
        Long productId = 10L;
        Long quantity = 1L;

        User user = new User();
        user.setId(userId);
        user.setCart(null); // Cart does not exist

        Product product = new Product();
        product.setId(productId);
        product.setName("Product B");
        product.setPrice(100L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Mock cart creation
        when(cartRepository.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        cartService.addItemToCart(userId, productId, quantity);

        // Assert
        assertNotNull(user.getCart());
        assertEquals(1, user.getCart().getItems().size());
        assertEquals(100L, user.getCart().getTotalPrice());

        verify(cartRepository).save(any(Cart.class)); // cart creation
        verify(userRepository).save(user); // link cart to user
        verify(cartRepository).saveAndFlush(user.getCart()); // final save
    }
}
