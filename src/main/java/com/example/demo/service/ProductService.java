package com.example.demo.service;

import com.example.demo.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductService() {
        loadProductsFromJson();
    }

    private void loadProductsFromJson() {
        try {
            ClassPathResource resource = new ClassPathResource("products.json");
            InputStream inputStream = resource.getInputStream();
            List<Product> loadedProducts = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
            
            // JSON Double -> BigDecimal Konvertierung
            for (Product product : loadedProducts) {
                if (product.getPrice() == null && product.getId() != null) {
                    // Notfall-Konvertierung wenn JSON keine korrekten BigDecimal Werte enthält
                    try {
                        double price = objectMapper.convertValue(product.getPrice(), Double.class);
                        product.setPrice(BigDecimal.valueOf(price));
                    } catch (Exception e) {
                        product.setPrice(BigDecimal.ZERO);
                    }
                }
            }
            
            products.addAll(loadedProducts);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Produkte aus der JSON-Datei", e);
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
} 