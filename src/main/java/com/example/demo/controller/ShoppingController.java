package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShoppingController {

    private final ShoppingService shoppingService;

    @Autowired
    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    // Warenkorb-Endpunkte
    @PostMapping("/cart")
    public ResponseEntity<Cart> createCart() {
        return new ResponseEntity<>(shoppingService.createCart(), HttpStatus.CREATED);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        return shoppingService.getCart(cartId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cart/{cartId}/items")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long cartId,
            @RequestBody Map<String, Object> payload) {
        
        Long productId = Long.valueOf(payload.get("productId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        
        return shoppingService.addToCart(cartId, productId, quantity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/cart/{cartId}/items/{productId}")
    public ResponseEntity<Cart> updateCartItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> payload) {
        
        int quantity = payload.get("quantity");
        
        return shoppingService.updateCartItemQuantity(cartId, productId, quantity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cart/{cartId}/items/{productId}")
    public ResponseEntity<Cart> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        
        return shoppingService.removeFromCart(cartId, productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        shoppingService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    // Bestellungs-Endpunkte
    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> payload) {
        Long cartId = Long.valueOf(payload.get("cartId").toString());
        String customerName = (String) payload.get("customerName");
        String customerEmail = (String) payload.get("customerEmail");
        String shippingAddress = (String) payload.get("shippingAddress");
        
        return shoppingService.placeOrder(cartId, customerName, customerEmail, shippingAddress)
                .map(order -> new ResponseEntity<>(order, HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return shoppingService.getOrder(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(shoppingService.getAllOrders());
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> payload) {
        
        Order.OrderStatus status = Order.OrderStatus.valueOf(payload.get("status"));
        
        return shoppingService.updateOrderStatus(orderId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 