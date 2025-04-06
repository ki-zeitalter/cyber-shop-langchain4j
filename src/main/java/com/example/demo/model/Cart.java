package com.example.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long id;
    private List<CartItem> items = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    // Konstruktoren
    public Cart() {
    }

    public Cart(Long id) {
        this.id = id;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        recalculateTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }

    // Hilfsmethoden
    public void addItem(CartItem item) {
        // Prüfen, ob das Produkt bereits im Warenkorb ist
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getId().equals(item.getProduct().getId())) {
                // Wenn ja, nur die Menge erhöhen
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                recalculateTotal();
                return;
            }
        }
        
        // Wenn nicht, neues Item hinzufügen
        items.add(item);
        recalculateTotal();
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
        recalculateTotal();
    }

    public void updateItemQuantity(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                recalculateTotal();
                return;
            }
        }
    }

    public void clear() {
        items.clear();
        total = BigDecimal.ZERO;
    }

    private void recalculateTotal() {
        total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
    }
} 