package com.example.demo.assistant;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.P;
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
    public Product productInformation(String productName) {
        return productService.getProductByName(productName).orElseThrow(() -> new RuntimeException("Produkt nicht gefunden."));
    }

    @Tool("Diese Funktion fügt ein Produkt mit dem angegebenen Produktnamen zum Warenkorb mit der angegebenen Warenkorb-ID hinzu.")
    public String addProductToCart(@P("Der Name des Produkts") String productName, @P("Die ID des Warenkorbs")Long cartId) {
        var optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return "Produkt nicht gefunden.";
        }

        shoppingService.addToCart(cartId, optionalProduct.get().getId(), 1);

        return "Produkt '" + productName + "' wurde zum Warenkorb hinzugefügt.";
    }
}
