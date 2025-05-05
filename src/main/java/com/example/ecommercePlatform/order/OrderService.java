package com.example.ecommercePlatform.order;

import com.example.ecommercePlatform.cart.persistance.Cart;
import com.example.ecommercePlatform.cart.persistance.CartRepository;
import com.example.ecommercePlatform.order.model.OrderRequest;
import com.example.ecommercePlatform.order.persistance.Order;
import com.example.ecommercePlatform.order.persistance.OrderItem;
import com.example.ecommercePlatform.order.persistance.OrderRepository;
import com.example.ecommercePlatform.order.persistance.OrderStatus;
import com.example.ecommercePlatform.product.persistance.ProductRepository;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    public Order placeOrder(OrderRequest request) {
        Long userId = request.getUserId();

        // 1. Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get user's cart
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3. Create a new Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setPlacedAt(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());

        // 4. Convert cart items to order items
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setName(cartItem.getName());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.getOrderItems().addAll(orderItems);

        // 5. Save order
        Order savedOrder = orderRepository.save(order);

        // 6. Clear the cart (optional)
        cart.getItems().clear();
        cart.setTotalPrice(0L);
        cartRepository.save(cart);

        return savedOrder;
    }


    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }


    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }





}
