package com.example.demo.assistant;

import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopTools {
    private final ShoppingService shoppingService;
    private final ProductService productService;

}
