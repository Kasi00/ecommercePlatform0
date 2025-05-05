package com.example.ecommercePlatform;

import com.example.ecommercePlatform.cart.persistance.Cart;
import com.example.ecommercePlatform.cart.persistance.CartItem;
import com.example.ecommercePlatform.cart.persistance.CartRepository;
import com.example.ecommercePlatform.order.OrderService;
import com.example.ecommercePlatform.order.model.OrderRequest;
import com.example.ecommercePlatform.order.persistance.Order;
import com.example.ecommercePlatform.order.persistance.OrderRepository;
import com.example.ecommercePlatform.order.persistance.OrderStatus;
import com.example.ecommercePlatform.product.persistance.Product;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;


    @InjectMocks
    private OrderService orderService;

   @Test
    public void placeOrder() {
       // Arrange
       Long userId = 1L;

       // Mock order request
       OrderRequest request = new OrderRequest();
       request.setUserId(userId);

       // Mock user
       User user = new User();
       user.setId(userId);

       // Mock product
       Product product = new Product();
       product.setId(1L);
       product.setName("Product A");

       // Mock cart item
       CartItem cartItem = new CartItem();
       cartItem.setId(1L);
       cartItem.setName("Product A");
       cartItem.setQuantity(2L);
       cartItem.setPrice(150L);
       cartItem.setProduct(product);

       // Mock cart
       Cart cart = new Cart();
       cart.setId(1L);
       cart.setTotalPrice(300L);
       cart.setItems(new ArrayList<>(List.of(cartItem)));
       cart.setUser(user);
       cartItem.setCart(cart);
       user.setCart(cart);

       when(userRepository.findById(userId)).thenReturn(Optional.of(user));
       when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

       // Act
       Order placedOrder = orderService.placeOrder(request);

       // Assert
       assertNotNull(placedOrder);
       assertEquals(OrderStatus.PENDING, placedOrder.getStatus());
       assertEquals(300L, placedOrder.getTotalAmount());
       assertEquals(1, placedOrder.getOrderItems().size());
       assertEquals("Product A", placedOrder.getOrderItems().get(0).getName());
       assertEquals(2L, placedOrder.getOrderItems().get(0).getQuantity());
       verify(orderRepository, times(1)).save(any(Order.class));
       verify(cartRepository, times(1)).save(any(Cart.class));


   }

}
