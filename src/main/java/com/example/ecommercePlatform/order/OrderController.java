package com.example.ecommercePlatform.order;

import com.example.ecommercePlatform.order.model.OrderRequest;
import com.example.ecommercePlatform.order.persistance.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(){
        orderService.getOrders();
        return ResponseEntity.ok(orderService.getOrders());
    }


    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }


    @DeleteMapping("/{orderId}")
    public void delete(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }










}
