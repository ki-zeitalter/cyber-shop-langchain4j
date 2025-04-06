package com.example.demo.assistant;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRagService {

    private final InMemoryEmbeddingStore<TextSegment> embeddingStore;
    private final ProductService productService;

    private final ObjectMapper objectMapper;

    public void processProducts() {
        var products = productService.getAllProducts();

        var textSegments = products.stream()
                .map(product ->
                        Document.from(generateDescriptionForProduct(product),
                                new Metadata()
                                        .put("Produktname", product.getName())
                                        .put("Beschreibung", product.getDescription())
                                        .put("Preis", product.getPrice() != null ? product.getPrice().toString() : "N/A")
                                        .put("Produkt-ID", product.getId())
                                        .put("Bildadresse", product.getImageUrl())
                        )
                )
                .toList();

        embed(textSegments);
    }

    @SneakyThrows
    private String generateDescriptionForProduct(Product product) {
        return objectMapper.writeValueAsString(product);
    }

    private void embed(List<Document> documents) {
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
    }

    @PostConstruct
    public void init() {
        processProducts();
    }
}
