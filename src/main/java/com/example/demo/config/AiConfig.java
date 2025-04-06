package com.example.demo.config;

import com.example.demo.assistant.ShopAssistant;
import com.example.demo.assistant.ShopTools;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ShopAssistant assistant(
            StreamingChatLanguageModel streamingModel,
            ShopTools shopTools,
            ContentRetriever retriever) {
        return AiServices.builder(ShopAssistant.class)
                .streamingChatLanguageModel(streamingModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(retriever)
                .tools(shopTools)
                .build();
    }

    @Bean
    public InMemoryEmbeddingStore<TextSegment> embeddingStoreIngestor() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public EmbeddingStoreContentRetriever retriever(InMemoryEmbeddingStore<TextSegment> embeddingStore) {
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }
}
