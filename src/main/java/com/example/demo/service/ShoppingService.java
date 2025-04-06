package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShoppingService {

    private final ProductService productService;
    
    // In-Memory-Speicherung für Warenkörbe und Bestellungen
    private final Map<Long, Cart> carts = new HashMap<>();
    private final Map<Long, Order> orders = new HashMap<>();
    
    // IDs generieren
    private final AtomicLong cartIdCounter = new AtomicLong(1);
    private final AtomicLong orderIdCounter = new AtomicLong(1);

    @Autowired
    public ShoppingService(ProductService productService) {
        this.productService = productService;
    }

    // Warenkorb-Methoden
    public Cart createCart() {
        Long cartId = cartIdCounter.getAndIncrement();
        Cart newCart = new Cart(cartId);
        carts.put(cartId, newCart);
        return newCart;
    }

    public Optional<Cart> getCart(Long cartId) {
        return Optional.ofNullable(carts.get(cartId));
    }

    public Optional<Cart> addToCart(Long cartId, Long productId, int quantity) {
        // Warenkorb suchen
        Cart cart = carts.get(cartId);
        if (cart == null) {
            return Optional.empty();
        }

        // Produkt suchen
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isEmpty()) {
            return Optional.empty();
        }

        // Zum Warenkorb hinzufügen
        CartItem item = new CartItem(productOpt.get(), quantity);
        cart.addItem(item);
        
        return Optional.of(cart);
    }

    public Optional<Cart> updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            return Optional.empty();
        }

        cart.updateItemQuantity(productId, quantity);
        return Optional.of(cart);
    }

    public Optional<Cart> removeFromCart(Long cartId, Long productId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            return Optional.empty();
        }

        cart.removeItem(productId);
        return Optional.of(cart);
    }

    public void clearCart(Long cartId) {
        Cart cart = carts.get(cartId);
        if (cart != null) {
            cart.clear();
        }
    }

    // Bestellmethoden
    public Optional<Order> placeOrder(Long cartId, String customerName, String customerEmail, String shippingAddress) {
        // Warenkorb prüfen
        Cart cart = carts.get(cartId);
        if (cart == null || cart.getItems().isEmpty()) {
            return Optional.empty();
        }

        // Bestellung erstellen
        Long orderId = orderIdCounter.getAndIncrement();
        Order order = new Order(
            orderId,
            customerName,
            customerEmail,
            shippingAddress,
            new ArrayList<>(cart.getItems()) // Kopie der Items erstellen
        );

        // Bestellung speichern
        orders.put(orderId, order);
        
        // Warenkorb leeren
        cart.clear();
        
        return Optional.of(order);
    }

    public Optional<Order> getOrder(Long orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Optional<Order> updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orders.get(orderId);
        if (order == null) {
            return Optional.empty();
        }

        order.setStatus(status);
        return Optional.of(order);
    }
} 