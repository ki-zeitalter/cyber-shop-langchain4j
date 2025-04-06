package com.example.demo.assistant;

import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopTools {
    private final ShoppingService shoppingService;
    private final ProductService productService;

    private final ObjectMapper objectMapper;

    @Tool("Diese Funktion gibt die Produktinformationen für das Produkt mit dem angegebenen Produktnamen zurück.")
    public String productInformation(String productName) {
        return productService.getProductByName(productName).map(product -> {
            try {
                return objectMapper.writeValueAsString(product);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).orElse("Produkt nicht gefunden.");
    }

    public String addProductToCart(String productName) {
        return "Aktuell steht diese Funktion nicht zur Verfügung.";
    }
}
