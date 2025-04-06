package com.example.demo.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import reactor.core.publisher.Flux;

public interface ShopAssistant {
    @SystemMessage("Du bist der Assistent für den Cyberwaren-Shop 'Die Firma'. Du hilfst bei allen Fragen rund um Produkte, Shop, Bestellungen und Anliegen, wie Beschwerden etc.")
    Flux<String> chatStream(UserMessage userMessage);
}
